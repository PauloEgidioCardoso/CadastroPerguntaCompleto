package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import model.IndicacaoDificuldade;

public class IndicacaoListModel extends AbstractListModel<IndicacaoDificuldade>{
private List<IndicacaoDificuldade> indicacoes = new ArrayList<>();

public IndicacaoListModel(List<IndicacaoDificuldade> indicacoes) {
    this.indicacoes = indicacoes;
}
    
    @Override
    public IndicacaoDificuldade getElementAt(int index) {
	IndicacaoDificuldade indicacao = null;
	
	if (index >= 0 && index < getSize()) {
	    indicacao = indicacoes.get(index);
	}
	return indicacao;
    }

    @Override
    public int getSize() {
	return indicacoes.size();
    }

    public void carregar(List<IndicacaoDificuldade> indicacoes) {
	this.indicacoes = indicacoes;
	fireContentsChanged(this, 0, getSize());
    }		
    public void remover(IndicacaoDificuldade indicacao) {
	int index = indicacoes.indexOf(indicacao);
	
	if(index >=0) {
	    indicacoes.remove(index);
	    fireContentsChanged(this, index, index);
	}

    }
    
    public void inserir (IndicacaoDificuldade indicacao) {
	indicacoes.add(indicacao);
	fireContentsChanged(this, getSize() -1, getSize() -1);
    }
    
    public List<IndicacaoDificuldade> getIndicacoes(){
	return indicacoes;
    }
} //fim da classe IndicacaoLipsModel
