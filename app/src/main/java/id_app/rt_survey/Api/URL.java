package id_app.rt_survey.Api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Carlos_Lopez on 3/12/16.
 */
public enum  URL  {

    LIST(0,"api/order/getbyuser/","GET"),
    LOGIN(1,"token","POST");

    // GET=0,
    // POST=1,
    // PUT=2,
    // DELETE=3

    private final String MAIN_URL="http://190.52.97.182/service/";
    private Integer mTYPE;
    private String  mFINAL;
    private String  mNAME_TYPE;

    private URL(Integer TYPE,String FINAL,String NAME_TYPE) {
        this.mTYPE=TYPE;
        this.mFINAL=(MAIN_URL+FINAL);
        this.mNAME_TYPE=NAME_TYPE;
    }

    public String getURL() {
        return mFINAL;
    }

    public String getRequestName() {
        return mNAME_TYPE;
    }

    public Integer getRequestType() {
        return mTYPE;
    }
}
