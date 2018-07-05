package com.github.commonlibs.libwebview.hioscommon;

import com.github.commonlibs.libwebview.hios2.HiosAlias;

public class HiosRegister {

    private static final String PKG_SFNATION = "com.haier.cellarette.libwebview";

    public static void load() {

        HiosAlias.register("jump.HiosMainActivity", PKG_SFNATION, ".activity.HiosMainActivity");
//        HiosAlias.register("jump.webviewmainactivity", PKG_SFNATION, ".base.WebViewMainActivity");

    }
}
