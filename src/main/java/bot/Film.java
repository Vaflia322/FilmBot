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
    @Override
    public String toString(){
        return "Название: " + name + "\n" +
                "Описание: " + description + "\n" +
                "Рейтинг кинопоиска: " + rating + "\n";
    }
}