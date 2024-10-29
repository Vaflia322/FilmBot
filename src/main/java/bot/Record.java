package bot;

public class Record {
    private final String name;
    private final String description;
    private final String rating;
    private final String genres;

    public Record(String name, String description, String rating,String genres) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.genres = genres;
    }
    @Override
    public String toString(){
        return "Название: " + name + "\n" +
                "Описание: " + description + "\n" +
                "Рейтинг кинопоиска: " + rating + "\n"+
                "Жанры: " + genres;

    }
}