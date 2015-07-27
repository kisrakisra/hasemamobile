package vizyonmedya.testapplication;

/**
 * Created by Verisun on 21.7.2015.
 */
public class URUNSECENEKLER
{
    private SECENEK[] SECENEK;

    public SECENEK[] getSECENEK ()
    {
        return SECENEK;
    }

    public void setSECENEK (SECENEK[] SECENEK)
    {
        this.SECENEK = SECENEK;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SECENEK = "+SECENEK+"]";
    }
}
