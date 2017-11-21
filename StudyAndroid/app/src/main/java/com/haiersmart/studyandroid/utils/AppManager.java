package com.haiersmart.studyandroid.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Gui Yan on 2017/11/20.
 */

public class AppManager {
    private Stack<Activity> mActivities = new Stack();
    private static AppManager sInstance;

    public static AppManager getInstance() {
        if (sInstance == null) {
            sInstance = new AppManager();
        }

        return sInstance;
    }

    private AppManager() {
    }

    public void add(Activity activity) {
        this.mActivities.add(activity);
    }

    public void pop() {
        if (!this.mActivities.isEmpty()) {
            Activity a = (Activity) this.mActivities.lastElement();
            this.remove(a);
        }
    }

    public void remove(Activity activity) {
        if (activity != null) {
            this.mActivities.remove(activity);
        }
    }

    public void finish(Class<?> activity) {
        if (!this.mActivities.isEmpty()) {
            Iterator var2 = this.mActivities.iterator();

            Activity a;
            do {
                if (!var2.hasNext()) {
                    return;
                }

                a = (Activity) var2.next();
            } while (!a.getClass().equals(activity));

            a.finish();
            this.remove(a);
        }
    }

    public Activity get(Class<?> klass) {
        Iterator var2 = this.mActivities.iterator();

        Activity a;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            a = (Activity) var2.next();
        } while (!a.getClass().equals(klass));

        return a;
    }

    public Stack<Activity> getAll() {
        return this.mActivities;
    }

    public Activity top() {
        return this.mActivities.isEmpty() ? null : (Activity) this.mActivities.peek();
    }
}
