/**
 * Watches the search queries
 */
public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        // Primeiro observador: notifica consultas que contêm a palavra "friend"
        model.addQueryObserver(new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("Oh Yes! " + query);
            }
        }, new WebSearchModel.QueryFilter() {
            @Override
            public boolean isInteresting(String query) {
                // Verifica se a consulta contém "friend" (case insensitive)
                return query.toLowerCase().contains("friend");
            }
        });

        // Segundo observador: notifica consultas com mais de 60 caracteres
        model.addQueryObserver(new WebSearchModel.QueryObserver() {
            @Override
            public void onQuery(String query) {
                System.out.println("So long " + query);
            }
        }, new WebSearchModel.QueryFilter() {
            @Override
            public boolean isInteresting(String query) {
                // Verifica se a consulta tem mais de 60 caracteres
                return query.length() > 60;
            }
        });
    }
}