package Serialisation;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qdai
 */
public abstract class Serialisation {
    public abstract void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
