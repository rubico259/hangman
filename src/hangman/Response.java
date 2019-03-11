package hangman;


public class Response {

    private String hang;
    private Boolean correct;

    public Response(String hang, Boolean correct) {
        this.hang = hang;
        this.correct = correct;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
