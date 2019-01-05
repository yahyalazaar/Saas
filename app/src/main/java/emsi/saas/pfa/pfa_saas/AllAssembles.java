package emsi.saas.pfa.pfa_saas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import emsi.saas.pfa.pfa_saas.services.AssembleServices;

public class AllAssembles extends AppCompatActivity {


    private static AllAssembles mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_villes);
        mContext = this;
        new AssembleServices().Assembles();



    }

    public static AllAssembles getContext() {
        return mContext;
    }


}
