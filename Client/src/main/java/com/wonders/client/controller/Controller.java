package com.wonders.client.controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface Controller {

    void server_updates() throws MalformedURLException, NotBoundException, RemoteException;
}
