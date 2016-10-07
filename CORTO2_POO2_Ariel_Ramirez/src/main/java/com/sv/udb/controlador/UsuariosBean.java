/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.UsuariosFacadeLocal;
import com.sv.udb.modelo.Usuarios;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ariel
 */
@Named(value = "usuariosBean")
@ViewScoped
public class UsuariosBean implements Serializable{
    @EJB
    private UsuariosFacadeLocal FCDEUsua;    
    private Usuarios objeUsua;
    private List<Usuarios> listUsua;
    private boolean guardar;

 private static Logger log = Logger.getLogger(UsuariosBean.class);
   
    public Usuarios getObjeAlum() {
        return objeUsua;
    }

    public void setObjeAlum(Usuarios objeUsua) {
        this.objeUsua = objeUsua;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Usuarios> getListAlum() {
        return listUsua;
    }
    
    /**
     * Creates a new instance of UsuariosBean
     */
    
    public UsuariosBean() {
    }
    
    @PostConstruct
    public void init()
    {
        
        this.objeUsua = new Usuarios();
         log.debug("Se creo un nuevo objeto");
        this.guardar = true;
        
        this.consTodo();
    }
    
    public void limpForm()
    {
         
        this.objeUsua = new Usuarios();
        log.debug("Formulario limpiado, se creo un nuevo objeto");
        this.guardar = true;        
    }
    
    public void guar()
    {
        
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEUsua.create(this.objeUsua);
            this.listUsua.add(this.objeUsua);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Datos guardados");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
             log.error(getRootCause(ex).getMessage());
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listUsua.remove(this.objeUsua); //Limpia el objeto viejo
            FCDEUsua.edit(this.objeUsua);
            this.listUsua.add(this.objeUsua); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            log.info("Datos Modificados");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
             log.error(getRootCause(ex).getMessage());
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEUsua.remove(this.objeUsua);
            this.listUsua.remove(this.objeUsua);
            this.limpForm();
            
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            log.info("Datos Eliminados");
            
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
             log.error(getRootCause(ex).getMessage());
        }
        finally
        {
            
        }
    }
    
    public void consTodo()
    {
        
        try
        {
            this.listUsua = FCDEUsua.findAll();
            log.info("Todos los datos consultados");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
             log.error(getRootCause(ex).getMessage());
        }
        finally
        {
            
        }
    }
    
    public void cons()
    {
       
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiAlumPara"));
        try
        {
            this.objeUsua = FCDEUsua.find(codi);
            this.guardar = false;
            
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s ", this.objeUsua.getAcceUsua()));
            log.info( "Consultado a " + 
                    String.format("%s ", this.objeUsua.getAcceUsua()));
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
             log.error(getRootCause(ex).getMessage());
        }
        finally
        {
            
        }
    }
}
