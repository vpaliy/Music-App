package com.vpaliy.data.source.local.handler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.vpaliy.data.source.local.utils.DatabaseUtils;
import com.vpaliy.domain.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.vpaliy.data.source.local.MusicContract.Users;

@SuppressWarnings({"unused","WeakerAccess"})
public class UserHandler {

    private ContentProvider provider;

    public UserHandler(ContentProvider provider){
        this.provider=provider;
    }

    public List<User> queryAll(Query query){
        if(query!=null){
            Cursor cursor=provider.query(Users.CONTENT_URI,null,query.selection(),query.args(),null);
            return queryAll(cursor);
        }
        return queryAll();
    }

    public User query(String id){
        if(!TextUtils.isEmpty(id)){
            Cursor cursor=provider.query(Users.buildUserUri(id),null,null,null,null);
            if(cursor!=null){
                User user=DatabaseUtils.toUser(cursor);
                if(!cursor.isClosed()) cursor.close();
                return user;
            }
            return null;
        }
        throw new IllegalArgumentException("Id is null");
    }

    private List<User> queryAll(){
        Cursor cursor=provider.query(Users.CONTENT_URI,null,null,null,null);
        return queryAll(cursor);
    }

    private List<User> queryAll(Cursor cursor){
        if(cursor!=null){
            List<User> users=new ArrayList<>(cursor.getCount());
            while(cursor.moveToNext()){
                User user= DatabaseUtils.toUser(cursor);
                users.add(user);
            }
            if(!cursor.isClosed()) cursor.close();
            return users;
        }
        return null;
    }

    public void insert(User user){
        if (user != null) {
            ContentValues values=DatabaseUtils.toValues(user);
            provider.insert(Users.CONTENT_URI,values);
        }
    }
}
