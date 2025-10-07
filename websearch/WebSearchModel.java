import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform "web search" (from a file), notify the interested observers of each query.
 */
public class WebSearchModel {
    private final File sourceFile;
    // Lista para armazenar os observadores junto com seus filtros
    private final List<QueryObserverInfo> observerInfos = new ArrayList<>();

    // Interface para os observadores de consulta
    public interface QueryObserver {
        void onQuery(String query);
    }
    
    // Interface Strategy para os filtros de consulta
    public interface QueryFilter {
        /**
         * Determina se uma consulta é interessante para o observador
         * @param query a consulta a ser verificada
         * @return true se o observador deve ser notificado, false caso contrário
         */
        boolean isInteresting(String query);
    }
    
    // Classe interna para armazenar o observador e seu filtro associado
    private static class QueryObserverInfo {
        final QueryObserver observer;
        final QueryFilter filter;
        
        QueryObserverInfo(QueryObserver observer, QueryFilter filter) {
            this.observer = observer;
            this.filter = filter;
        }
    }

    public WebSearchModel(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Simula a busca lendo o arquivo e notificando os observadores
     */
    public void pretendToSearch() {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                notifyAllObservers(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra um observador com um filtro específico
     * @param queryObserver o observador a ser registrado
     * @param queryFilter o filtro que determina quais consultas notificar
     */
    public void addQueryObserver(QueryObserver queryObserver, QueryFilter queryFilter) {
        observerInfos.add(new QueryObserverInfo(queryObserver, queryFilter));
    }

    /**
     * Notifica todos os observadores que estão interessados na consulta
     * @param line a consulta a ser verificada e potencialmente notificada
     */
    private void notifyAllObservers(String line) {
        for (QueryObserverInfo info : observerInfos) {
            // Verifica se o observador está interessado nesta consulta usando o filtro
            if (info.filter.isInteresting(line)) {
                info.observer.onQuery(line);
            }
        }
    }
}