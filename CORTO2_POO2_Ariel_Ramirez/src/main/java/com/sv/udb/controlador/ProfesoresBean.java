/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.ProfesoresFacadeLocal;
import com.sv.udb.ejb.ProfesoresFacadeLocal;
import com.sv.udb.modelo.Profesores;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laboratorio
 */
@Named(value = "profesoresBean")
@ViewScoped
public class ProfesoresBean  implements Serializable {

    @EJB
    private ProfesoresFacadeLocal FCDEAProf;

    private static Logger log = Logger.getLogger(ProfesoresBean.class);
    
    private Profesores objeProf;
    private List<Profesores> listProf;
    private boolean guardar;

    public Profesores getObjeProf() {
        return objeProf;
    }

    public void setObjeAlum(Profesores objeProf) {
        this.objeProf = objeProf;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Profesores> getListProf() {
        return listProf;
    }
    
    /**
     * Creates a new instance of ProfesoresBean
     */
    
    public ProfesoresBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeProf = new Profesores();
         log.debug("Se creo un nuevo objeto");
        this.guardar = true;
        this.consTodo();
    }
    
    public void limpForm()
    {
        this.objeProf = new Profesores();
         log.debug("Formulario limpiado, se creo un nuevo objeto");
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEAProf.create(this.objeProf);
            this.listProf.add(this.objeProf);
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
            this.listProf.remove(this.objeProf); //Limpia el objeto viejo
            FCDEAProf.edit(this.objeProf);
            this.listProf.add(this.objeProf); //Agrega el objeto modificado
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
            FCDEAProf.remove(this.objeProf);
            this.listProf.remove(this.objeProf);
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
            this.listProf = FCDEAProf.findAll();
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
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiProfPara"));
        try
        {
            this.objeProf = FCDEAProf.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeProf.getNombProf(), this.objeProf.getApelProf()) + "')");
            log.info( "Consultado a " + 
                    String.format("%s %s", this.objeProf.getNombProf(), this.objeProf.getApelProf()) );
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
