package com.proptit.android.ptitme.module;



import com.proptit.android.ptitme.PtitMeApplication;
import com.proptit.android.ptitme.base.BaseActivity;
import com.proptit.android.ptitme.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by diegonovati on 18/04/2018.
 */

@Singleton
@Component(modules = { NetworkingModule.class})
public interface AppComponent {

    void inject(PtitMeApplication application);

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

}
