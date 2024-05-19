package com.example.taskraken.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskraken.network.schemas.users.UserRead;

import java.util.UUID;

@Entity(tableName="cookies")
public class Cookie {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name="ID")
    private String id;

    private final String name;
    private final String value;
    private final long expiresAt;
    private final String domain;
    private final String path;
    private final boolean secure;
    private final boolean httpOnly;

    private final boolean persistent;
    private final boolean hostOnly;

    public Cookie(
            String name,
            String value,
            long expiresAt,
            String domain,
            String path,
            boolean secure,
            boolean httpOnly,
            boolean persistent,
            boolean hostOnly
    ) {
        this.id = domain+'@'+name;
        this.name = name;
        this.value = value;
        this.expiresAt = expiresAt;
        this.domain = domain;
        this.path = path;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.persistent = persistent;
        this.hostOnly = hostOnly;
    }

    public Cookie(okhttp3.Cookie cookie){
        this(
                cookie.name(),
                cookie.value(),
                cookie.expiresAt(),
                cookie.domain(),
                cookie.path(),
                cookie.secure(),
                cookie.httpOnly(),
                cookie.persistent(),
                cookie.hostOnly()
        );
    }

    public okhttp3.Cookie getCookie(){
        okhttp3.Cookie.Builder builder = new okhttp3.Cookie.Builder();
        builder
                .name(name)
                .value(value)
                .expiresAt(expiresAt)
                .path(path);
        if (hostOnly)
            builder.hostOnlyDomain(domain);
        else
            builder.domain(domain);
        if (secure) builder.secure();
        if (httpOnly) builder.httpOnly();

        return builder.build();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id){
        this.id = id;
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > expiresAt;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecure() {
        return secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean isHostOnly() {
        return hostOnly;
    }

    @NonNull
    @Override
    public String toString() {
        return "Cookie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", expiresAt=" + expiresAt +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                ", persistent=" + persistent +
                ", hostOnly=" + hostOnly +
                '}';
    }
}
