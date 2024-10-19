package bot;


public final class Fault extends ApiObject{
    private String error;

    public Fault(String error) {
        this.error = error;
    }

    public String getError(){
        return error;
    }
}
