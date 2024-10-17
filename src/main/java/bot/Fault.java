package bot;


public final class Fault extends ApiObject{
    private String error;
    public void setError(String error) {
        this.error = error;
    }
    public String getError(){
        return error;
    }
}
