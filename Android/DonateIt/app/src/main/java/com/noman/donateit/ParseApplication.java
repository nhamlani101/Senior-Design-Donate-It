package com.noman.donateit;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by noman on 3/23/17.
 */

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3uWGzctA6yEJlw10GVQsGWutyvQ2huZF1C4AUVk9")
                .clientKey("AoGXmTe5HWs56bYVtylagNJsV4nqbnLX3kxfxPlf")
                .server("https://parseapi.back4app.com/").build()
        );
    }
}
