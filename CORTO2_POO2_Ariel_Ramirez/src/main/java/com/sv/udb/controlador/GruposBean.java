/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
import com.sv.udb.modelo.Grupos;
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
 * @author Mauricio
 */
@Named(value = "gruposBean")
@ViewScoped
public class GruposBean implements Serializable{

    @EJB
    private GruposFacadeLocal FCDEGrupos;
  private Grupos objeGrup;
    private List<Grupos> listGrup;
    private boolean guardar;
   private static Logger log = Logger.getLogger(GruposBean.class);
    public Grupos getObjeGrup() {
        return objeGrup;
    }

    public void setObjeGrup(Grupos objeGrup) {
        this.objeGrup = objeGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Grupos> getListGrup() {
        return listGrup;
    }
    
    /**
     * Creates a new instance of GruposBean
     */
    
    public GruposBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeGrup = new Grupos();
         log.debug("Se creo un nuevo objeto");
        this.guardar = true;
        this.consTodo();
    }
    
    public void limpForm()
    {
        this.objeGrup = new Grupos();
        log.debug("Formulario limpiado, se creo un nuevo objeto");
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEGrupos.create(this.objeGrup);
            this.listGrup.add(this.objeGrup);
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
            this.listGrup.remove(this.objeGrup); //Limpia el objeto viejo
            FCDEGrupos.edit(this.objeGrup);
            this.listGrup.add(this.objeGrup); //Agrega el objeto modificado
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
            FCDEGrupos.remove(this.objeGrup);
            this.listGrup.remove(this.objeGrup);
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
            this.listGrup = FCDEGrupos.findAll();
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
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiGrupPara"));
        try
        {
            this.objeGrup = FCDEGrupos.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s", this.objeGrup.getNombGrup()) + "')");
            log.info( "Consultado a " + 
                    String.format("%s", this.objeGrup.getNombGrup()));
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
