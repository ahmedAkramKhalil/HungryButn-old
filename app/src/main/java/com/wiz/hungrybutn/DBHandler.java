package com.wiz.hungrybutn;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DBHandler {
    private static DBHandler dbHandler;
    Realm realm ;

    private DBHandler(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance() ;
    }

    public static DBHandler getInstance(Context context) {
        if (dbHandler == null) {
            dbHandler = new DBHandler(context);

        }
        return dbHandler;
    }
    public void  addObjectsToDB(RealmObject realmObject){
       realm.beginTransaction();
       realm.copyToRealmOrUpdate(realmObject);
       realm.commitTransaction();
    }




}
