/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.UsuariosFacadeLocal;
import com.sv.udb.ejb.UsuariosRolesFacadeLocal;
import com.sv.udb.modelo.Usuarios;
import com.sv.udb.modelo.UsuariosRoles;
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
@Named(value = "UsuariosRolesBean")
@ViewScoped
public class UsuariosRolesBean implements Serializable{
    @EJB
    private UsuariosRolesFacadeLocal FCDEUsuaRole;    
    private UsuariosRoles objeUsuaRole;
    private List<UsuariosRoles> listUsuaRole;
    private boolean guardar;

 private static Logger log = Logger.getLogger(UsuariosRolesBean.class);
   
    public UsuariosRoles getObjeUsuaRole() {
        return objeUsuaRole;
    }

    public void setObjeUsuaRole(UsuariosRoles objeUsuaRole) {
        this.objeUsuaRole = objeUsuaRole;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<UsuariosRoles> getListUsuaRole() {
        return listUsuaRole;
    }
    
    /**
     * Creates a new instance of UsuariosRolesBean
     */
    
    public UsuariosRolesBean() {
    }
    
    @PostConstruct
    public void init()
    {
        
        this.objeUsuaRole = new UsuariosRoles();
         log.debug("Se creo un nuevo objeto");
        this.guardar = true;
        
        this.consTodo();
    }
    
    public void limpForm()
    {
         
        this.objeUsuaRole = new UsuariosRoles();
        log.debug("Formulario limpiado, se creo un nuevo objeto");
        this.guardar = true;        
    }
    
    public void guar()
    {
        
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEUsuaRole.create(this.objeUsuaRole);
            this.listUsuaRole.add(this.objeUsuaRole);
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
            this.listUsuaRole.remove(this.objeUsuaRole); //Limpia el objeto viejo
            FCDEUsuaRole.edit(this.objeUsuaRole);
            this.listUsuaRole.add(this.objeUsuaRole); //Agrega el objeto modificado
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
            FCDEUsuaRole.remove(this.objeUsuaRole);
            this.listUsuaRole.remove(this.objeUsuaRole);
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
            this.listUsuaRole = FCDEUsuaRole.findAll();
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
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiUsuaRolePara"));
        try
        {
            this.objeUsuaRole = FCDEUsuaRole.find(codi);
            this.guardar = false;
            
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado')");
           
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
