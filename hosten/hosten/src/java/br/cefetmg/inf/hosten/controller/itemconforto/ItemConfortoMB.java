package br.cefetmg.inf.hosten.controller.itemconforto;

import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;
import br.cefetmg.inf.hosten.proxy.ManterItemConfortoProxy;
import java.io.IOException;
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
    private String codItemAlterar;

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
    
    public void onRowInit(RowEditEvent event) {
        codItemAlterar = (String) event.getComponent().getAttributes().get("itemEditar");           
    }
    
    public void onRowEdit(RowEditEvent event) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        
        item = (ItemConforto) event.getObject();
        
        IManterItemConforto manterItem = new ManterItemConfortoProxy();
        try {
            boolean testeAlteracao = manterItem.alterar(codItemAlterar, item);
            if (testeAlteracao) {
                context.addMessage(null, new FacesMessage("Registro alterado com sucesso!"));
                return;
            } else {
                context.addMessage(null, new FacesMessage("Falha ao alterar o registro!"));
                return;
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(ex.getMessage()));
            FacesContext.getCurrentInstance().getExternalContext().redirect("itens-conforto.jsf");
            return;
        }
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
