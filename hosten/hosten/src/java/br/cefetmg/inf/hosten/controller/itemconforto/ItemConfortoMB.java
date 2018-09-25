package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;
import br.cefetmg.inf.hosten.proxy.ManterItemConfortoProxy;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "itemMB")
@ViewScoped
public class ItemConfortoMB implements Serializable {
    
    private List<ItemConforto> listaItens;
    private ItemConforto item;

    public ItemConfortoMB() {
        item = new ItemConforto(null, null);
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            listaItens = manterItem.listarTodos();
        } catch (Exception ex) {
            //
            //
            //
        }
    }

    public ItemConforto getItem() {
        return item;
    }

    public void setItem(ItemConforto item) {
        this.item = item;
    }
    
    public List<ItemConforto> getListaItens () {
        return listaItens;
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item de Conforto Editado", ((ItemConforto) event.getObject()).getCodItem());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edição Cancelada", ((ItemConforto) event.getObject()).getCodItem());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
        
    public String excluir (ItemConforto item) {
        this.item = item;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        
        try {
            boolean testeExclusao = manterItem.excluir(item.getCodItem());
            if (testeExclusao) {
                context.addMessage(null, new FacesMessage("Registro excluído com sucesso!"));
                return "sucesso";
            } else {
                context.addMessage(null, new FacesMessage("Falha ao excluir o registro!"));
                return "falha";
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            return "falha";
        }
        
    }
            
    public String inserir () {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        
        try {
            boolean testeInsercao = manterItem.inserir(item);
            if (testeInsercao) {
                context.addMessage(null, new FacesMessage("Registro inserido com sucesso!"));
                return "sucesso";
            } else {
                context.addMessage(null, new FacesMessage("Falha ao inserir o registro!"));
                return "falha";
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            return "falha";
        }  
    }

}
