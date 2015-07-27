package vizyonmedya.testapplication;

/**
 * Created by Verisun on 21.7.2015.
 */
public class MyXML {
    private URUNSECENEKLER URUNSECENEKLER;

    public URUNSECENEKLER getURUNSECENEKLER ()
    {
        return URUNSECENEKLER;
    }

    public void setURUNSECENEKLER (URUNSECENEKLER URUNSECENEKLER)
    {
        this.URUNSECENEKLER = URUNSECENEKLER;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [URUNSECENEKLER = "+URUNSECENEKLER+"]";
    }
}
