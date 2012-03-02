package josecgomez.com.android.dev.webservice.objects;
 
public class alerts {
 
    public int alertid;
    public String alerttext;
    public String alertdate;
 
    @Override
    public String toString()
    {
        return "Alert ID: "+alertid+ " Alert Text: "+alerttext+ " Alert Date: "+alertdate;
 
    }
}