package com.example.teamcity.api.requests;

public interface CrudInterface {
    // Методы указаны без тела - это значит, что при использовании этого интерфейса мы обязаны их все переопределить.
    Object create(Object obj);

    Object get(String id);

    Object update(String id, Object obj);

    Object delete(String id);
}
