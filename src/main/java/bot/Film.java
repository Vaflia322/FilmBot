package bot;

public class Film{
    private final String name;
    private final String description;
    private final String rating;

    public Film(String name, String description, String rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public String getStringFilm(){
        StringBuilder result = new StringBuilder().append("Название: ").append(name).append("\n");
        result.append("Описание: ").append(description).append("\n");
        result.append("Рейтинг кинопоиска: ").append(rating).append("\n");
        return result.toString();
    }
}