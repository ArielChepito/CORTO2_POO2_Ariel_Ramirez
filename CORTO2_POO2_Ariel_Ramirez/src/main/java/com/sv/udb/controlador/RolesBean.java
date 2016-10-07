/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.RolesFacadeLocal;
import com.sv.udb.modelo.Roles;
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
@Named(value = "rolesBean")
@ViewScoped
public class RolesBean implements Serializable{
    @EJB
    private RolesFacadeLocal FCDERole;    
    private Roles objeRole;
    private List<Roles> listRole;
    private boolean guardar;

 private static Logger log = Logger.getLogger(RolesBean.class);
   
    public Roles getObjeRole() {
        return objeRole;
    }

    public void setObjeRole(Roles objeRole) {
        this.objeRole = objeRole;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Roles> getListAlum() {
        return listRole;
    }
    
    /**
     * Creates a new instance of RolesBean
     */
    
    public RolesBean() {
    }
    
    @PostConstruct
    public void init()
    {
        
        this.objeRole = new Roles();
         log.debug("Se creo un nuevo objeto");
        this.guardar = true;
        
        this.consTodo();
    }
    
    public void limpForm()
    {
         
        this.objeRole = new Roles();
        log.debug("Formulario limpiado, se creo un nuevo objeto");
        this.guardar = true;        
    }
    
    public void guar()
    {
        
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDERole.create(this.objeRole);
            this.listRole.add(this.objeRole);
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
            this.listRole.remove(this.objeRole); //Limpia el objeto viejo
            FCDERole.edit(this.objeRole);
            this.listRole.add(this.objeRole); //Agrega el objeto modificado
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
            FCDERole.remove(this.objeRole);
            this.listRole.remove(this.objeRole);
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
            this.listRole = FCDERole.findAll();
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
            this.objeRole = FCDERole.find(codi);
            this.guardar = false;
            
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s", this.objeRole.getDescRole()));
            log.info( "Consultado a " + 
                    String.format("%s", this.objeRole.getDescRole()));
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
