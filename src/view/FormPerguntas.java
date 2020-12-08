package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.InsetsUIResource;

import model.Dificuldade;
import model.IndicacaoDificuldade;
import model.MnDB;
import model.Questao;

public class FormPerguntas extends JPanel {
    private AppFrame frame;

    private static final Insets FIELD_INSETS = new InsetsUIResource(5, 10, 0, 0);
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private JTextField idTxt;
    private JTextField perguntaTxt;
    private JTextField dicaTxt;
    private JTextField resposta;
    private JButton salvarBtn;
    private JButton cancelarBtn;

    private JRadioButton facil;
    private JRadioButton medio;
    private JRadioButton dificil;

    private ButtonGroup grupo1;

    private Questao questao;
    private QuestaoPanel panelAltern;
    private QuestaoPanel ePanel;
    private QuestaoPanel mPanel;
    private QuestaoPanel hPanel;

    private CardLayout layoutAltern;
    private JPanel cardsPanelAltern;

    private IndicacaoListModel naoSelecionadoModel;
    private JList<IndicacaoDificuldade> naoSelecionadoList;
    private IndicacaoListModel selecionadoModel;
    private JList<IndicacaoDificuldade> selecionadoList;
    private static final int LIST_VISIBLE_ROWS = 3;

    public FormPerguntas(AppFrame appFrame) {
	this.frame = appFrame;
	layout = new GridBagLayout();
	constraints = new GridBagConstraints();
	setBackground(new Color(0, 159, 136));
	questao = null;

	setLayout(layout);
	// Zerar campos do formulario.
	addComponentListener(new ComponentAdapter() {

	    @Override
	    public void componentShown(ComponentEvent e) {
		if (questao == null) {
		    idTxt.setText("");
		    perguntaTxt.setText("");
		    dicaTxt.setText("");
		    resposta.setText("");
		    naoSelecionadoModel.carregar(MnDB.listarIndicacao());
		    selecionadoModel.carregar(new ArrayList<>());

		    if (facil.isSelected()) {
			facil.setSelected(true);
			panelAltern.setAlternativa("", 0);
			panelAltern.setAlternativa("", 1);
		    } else if (medio.isSelected()) {
			medio.setSelected(true);
			panelAltern.setAlternativa("", 0);
			panelAltern.setAlternativa("", 1);
			panelAltern.setAlternativa("", 2);
		    } else {
			dificil.setSelected(true);
			panelAltern.setAlternativa("", 0);
			panelAltern.setAlternativa("", 1);
			panelAltern.setAlternativa("", 2);
			panelAltern.setAlternativa("", 3);
		    }

		} else {
		    idTxt.setText(Integer.toString(questao.getId()));
		    perguntaTxt.setText(questao.getPergunta());
		    dicaTxt.setText(questao.getDica());
		    resposta.setText(questao.getResposta());
		    selecionadoModel.carregar(questao.getIndicacao());
		    naoSelecionadoModel.carregar(MnDB.listarNaoSelecionados(questao.getId()));

		    if (questao.getDificuldade() == Dificuldade.FACIL) {
			facil.setSelected(true);
			panelAltern.setAlternativa(questao.getAlternativa(), 0);
			panelAltern.setAlternativa(questao.getAlternativa2(), 1);

		    } else if (questao.getDificuldade() == Dificuldade.MEDIO) {
			medio.setSelected(true);
			panelAltern.setAlternativa(questao.getAlternativa(), 0);
			panelAltern.setAlternativa(questao.getAlternativa2(), 1);
			panelAltern.setAlternativa(questao.getAlternativa3(), 2);
		    } else if (questao.getDificuldade() == Dificuldade.DIFICIL) {
			dificil.setSelected(true);
			panelAltern.setAlternativa(questao.getAlternativa(), 0);
			panelAltern.setAlternativa(questao.getAlternativa2(), 1);
			panelAltern.setAlternativa(questao.getAlternativa3(), 2);
			panelAltern.setAlternativa(questao.getAlternativa4(), 3);
		    }
		}

	    }
	});

	criarForm();
    }

    public void setQuestao(Questao questao) {
	this.questao = questao;
    }

    private void criarForm() {
	JLabel rotulo;
	rotulo = new JLabel("Id");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 0, 0);
	idTxt = new JTextField(30);
	idTxt.setEditable(false);
	addComponente(idTxt, 0, 1);

	rotulo = new JLabel("Pergunta");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 1, 0);
	perguntaTxt = new JTextField(30);
	perguntaTxt.setDocument(new LimitChar(100));
	addComponente(perguntaTxt, 1, 1);

	rotulo = new JLabel("Dica");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 2, 0);
	dicaTxt = new JTextField(30);
	dicaTxt.setDocument(new LimitChar(100));
	addComponente(dicaTxt, 2, 1);

	rotulo = new JLabel("Resposta");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 3, 0);
	resposta = new JTextField(30);
	resposta.setDocument(new LimitChar(100));
	addComponente(resposta, 3, 1);

	rotulo = new JLabel("Selecione a dificuldade da questão:");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 4, 0);
	colocarRadio();

	criarListaIndicacao();

	layoutAltern = new CardLayout();
	cardsPanelAltern = new JPanel();
	cardsPanelAltern.setLayout(layoutAltern);
	addComponente(cardsPanelAltern, 11, 0, 4, 4);
	criarCards();

	facil.setSelected(true);
	criarBtn();
    }

    private void criarListaIndicacao() {
	JLabel rotulo;
	JPanel panel;
	JScrollPane scroll;
	Dimension scrollPreferredDimension = new Dimension(120, 60);

	rotulo = new JLabel(" Informe a série indicada para essa pergunta:");
	rotulo.setForeground(Color.WHITE);
	addComponente(rotulo, 6, 1);

	panel = new JPanel();
	panel.setBackground(new Color(0, 159, 136));
	FlowLayout flowLayout = (FlowLayout) panel.getLayout();
	flowLayout.setAlignment(FlowLayout.LEFT);

	naoSelecionadoModel = new IndicacaoListModel(MnDB.listarIndicacao());
	naoSelecionadoList = new JList<IndicacaoDificuldade>(naoSelecionadoModel);
	naoSelecionadoList.setVisibleRowCount(LIST_VISIBLE_ROWS);
	naoSelecionadoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scroll = new JScrollPane(naoSelecionadoList);
	scroll.setPreferredSize(scrollPreferredDimension);
	panel.add(scroll);

	JButton moverBtn = new JButton("Mover");
	moverBtn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		IndicacaoDificuldade selecionado;

		if (naoSelecionadoList.getSelectedIndex() >= 0) {
		    selecionado = naoSelecionadoList.getSelectedValue();
		    selecionadoModel.inserir(selecionado);
		    naoSelecionadoModel.remover(selecionado);
		}

		if (selecionadoList.getSelectedIndex() >= 0) {
		    selecionado = selecionadoList.getSelectedValue();
		    naoSelecionadoModel.inserir(selecionado);
		    selecionadoModel.remover(selecionado);
		}
	    }
	});
	panel.add(moverBtn);

	selecionadoModel = new IndicacaoListModel(new ArrayList<>());
	selecionadoList = new JList<IndicacaoDificuldade>(selecionadoModel);
	selecionadoList.setVisibleRowCount(LIST_VISIBLE_ROWS);
	selecionadoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scroll = new JScrollPane(selecionadoList);
	scroll.setPreferredSize(scrollPreferredDimension);
	panel.add(scroll);

	addComponente(panel, 7, 1, 1, LIST_VISIBLE_ROWS);
    }

    // Parte do Polimorfismo
    public void criarAlternativas() {
	ePanel.painelAltenativas();
	mPanel.painelAltenativas();
	hPanel.painelAltenativas();
    }

    // Parte do Polimorfismo
    public void criarCards() {
	ePanel = new FacilQuestaoPanel();
	mPanel = new MediaQuestaoPanel();
	hPanel = new DificilQuestaoPanel();
	criarAlternativas();
	cardsPanelAltern.add(ePanel, FacilQuestaoPanel.class.getName()); // Adiciona o objeto da super classe dentro
									 // do cardsPanelAltern
	cardsPanelAltern.add(mPanel, MediaQuestaoPanel.class.getName()); // Adiciona o objeto da super classe dentro
									 // do cardsPanelAltern
	cardsPanelAltern.add(hPanel, DificilQuestaoPanel.class.getName()); // Adiciona o objeto da super classe
									   // dentro do cardsPanelAltern
    }

    // Parte do Polimorfismo
    public void alterCards() {
	if (facil.isSelected()) {
	    panelAltern = ePanel; // Troca o objeto dentro do panelAltern
	} else if (medio.isSelected()) {
	    panelAltern = mPanel; // Troca o objeto dentro do panelAltern
	} else if (dificil.isSelected()) {
	    panelAltern = hPanel; // Troca o objeto dentro do panelAltern
	}
    }

    // Metodo que cria os botões
    private void criarBtn() {
	JPanel btnPanel = new JPanel();
	FlowLayout flowLayout = (FlowLayout) btnPanel.getLayout();
	flowLayout.setAlignment(FlowLayout.CENTER);

	saveBtn(); // Chama o metodo que cria o botão salvar
	btnPanel.add(salvarBtn);
	cancelBtn();// Chama o metodo que cria o botão cancelar
	btnPanel.add(cancelarBtn);
	criarCards(); // Chama o metoo que cria os Cards
	btnPanel.setBackground(new Color(0, 159, 136));
	addComponente(btnPanel, 15, 1, 2, 1);

    }

    // Metodo que cria o botão salvar
    private void saveBtn() {
	salvarBtn = new JButton("Salvar");
	salvarBtn.addActionListener(new ActionListener() {
	    // metodo que "Escuta" a ação no botão salvar
	    @Override
	    public void actionPerformed(ActionEvent e) {
		questao = new Questao();

		questao.setIndicacao(selecionadoModel.getIndicacoes());

		if (facil.isSelected()) {
		    criarQuestaoFacil(); // cria a questão fácil
		} else if (medio.isSelected()) {
		    criarQuestaoMedia(); // cria a questão média
		} else if (dificil.isSelected()) {
		    criarQuestaoDificil(); // cria a questão difícil
		}
		if (idTxt.getText().isBlank()) {
		    if ((facil.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0) 
			    && (selecionadoModel.getSize() > 0)) // Verificação
														 // dos
														 // campos
														 // antes
														 // de
														 // salvar
														 // a
														 // questão
														 // fácil
		    {
			if (MnDB.inserir(questao)) {
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão criada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}

		    } else if ((medio.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0)
			    && (panelAltern.getAlternativa(2).length() > 0) 
			    && (selecionadoModel.getSize() > 0))// Verificação
														// dos
														// campos
														// antes
														// de
														// salvar
														// a
														// questão
														// média
		    {
			if (MnDB.inserir(questao)) {
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão criada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
		    } else if ((dificil.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0)
			    && (panelAltern.getAlternativa(2).length() > 0)
			    && (panelAltern.getAlternativa(3).length() > 0) 
			    && (selecionadoModel.getSize() > 0))// Verificação
														// dos
														// campos
														// antes
														// de
														// salvar
														// a
														// questão
														// difícil
		    {
			if (MnDB.inserir(questao)) {
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão criada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
		    }

		    criarCards();
		} else if (!idTxt.getText().isBlank()) {
		    if ((facil.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0)
			    && (selecionadoModel.getSize() > 0)) // Verificação dos campos antes de editar a
									     // questão fácil
		    {
			questao.setId(Integer.parseInt(idTxt.getText()));

			if (MnDB.atualizar(questao)) { // atualiza a questão no "Banco de dados"
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão Editada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}

		    } else if ((medio.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0)
			    && (panelAltern.getAlternativa(2).length() > 0)
			    && (selecionadoModel.getSize() > 0)) // Verificação dos campos antes de editar a
									     // questão média
		    {
			questao.setId(Integer.parseInt(idTxt.getText()));

			if (MnDB.atualizar(questao)) { // atualiza a questão no "Banco de dados"
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão Editada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
		    } else if ((dificil.isSelected()) && (perguntaTxt.getText().length() > 0)
			    && (resposta.getText().length() > 0) && (dicaTxt.getText().length() > 0)
			    && (panelAltern.getAlternativa(0).length() > 0)
			    && (panelAltern.getAlternativa(1).length() > 0)
			    && (panelAltern.getAlternativa(2).length() > 0)
			    && (panelAltern.getAlternativa(3).length() > 0)
			    && (selecionadoModel.getSize() > 0)) // Verificação dos campos antes de editar a
									     // questão difícil
		    {
			questao.setId(Integer.parseInt(idTxt.getText()));

			if (MnDB.atualizar(questao)) { // atualiza a questão no "Banco de dados"
			    JOptionPane.showMessageDialog(FormPerguntas.this, "Questão Editada com sucesso!",
				    AppFrame.TITULO, JOptionPane.INFORMATION_MESSAGE);// Mensagem de confirmação ao
										      // usuário
			} else {
			    JOptionPane.showMessageDialog(FormPerguntas.this,
				    "Ocorreu um erro durante a execução! Contate o administrador do sistema.",
				    AppFrame.TITULO, JOptionPane.WARNING_MESSAGE);
			    System.exit(0);
			}
		    }

		}
		ePanel.setAlternativa("", 0);
		ePanel.setAlternativa("", 1);
		mPanel.setAlternativa("", 0);
		mPanel.setAlternativa("", 1);
		mPanel.setAlternativa("", 2);
		hPanel.setAlternativa("", 0);
		hPanel.setAlternativa("", 1);
		hPanel.setAlternativa("", 2);
		hPanel.setAlternativa("", 3);
		frame.mostrarPerguntas();// volta para a lista de perguntas
	    }
	});

    }

    // Metodo que cria a questão Fácil
    public void criarQuestaoFacil() {
	if ((perguntaTxt.getText().length() > 0) && (resposta.getText().length() > 0)
		&& (dicaTxt.getText().length() > 0) && (panelAltern.getAlternativa(0).length() > 0)
		&& (panelAltern.getAlternativa(1).length() > 0) && (selecionadoModel.getSize() > 0)) {
	    questao.setPergunta(perguntaTxt.getText());
	    questao.setResposta(resposta.getText());
	    questao.setDica(dicaTxt.getText());
	    questao.setAlternativa(panelAltern.getAlternativa(0));
	    questao.setAlternativa2(panelAltern.getAlternativa(1));
	    questao.setDificuldade(Dificuldade.FACIL);
	} else {
	    JOptionPane.showMessageDialog(FormPerguntas.this,
		    "Você esqueceu de preencher/selecionar um campo.\nVerifique e tente novamente!", AppFrame.TITULO,
		    JOptionPane.ERROR_MESSAGE);// mensagem de erro par alertar o usuário que tem campos vazios
	}
    }

    // Metodo que cria a questão Média
    public void criarQuestaoMedia() {
	if ((perguntaTxt.getText().length() > 0) && (resposta.getText().length() > 0)
		&& (dicaTxt.getText().length() > 0) && (panelAltern.getAlternativa(0).length() > 0)
		&& (panelAltern.getAlternativa(1).length() > 0) && (panelAltern.getAlternativa(2).length() > 0)
		&& (selecionadoModel.getSize() > 0)) {
	    questao.setPergunta(perguntaTxt.getText());
	    questao.setResposta(resposta.getText());
	    questao.setDica(dicaTxt.getText());
	    questao.setAlternativa(panelAltern.getAlternativa(0));
	    questao.setAlternativa2(panelAltern.getAlternativa(1));
	    questao.setAlternativa3(panelAltern.getAlternativa(2));
	    questao.setDificuldade(Dificuldade.MEDIO);
	} else {
	    JOptionPane.showMessageDialog(FormPerguntas.this,
		    "Você esqueceu de preencher/selecionar um campo.\nVerifique e tente novamente!", AppFrame.TITULO,
		    JOptionPane.ERROR_MESSAGE);// mensagem de erro par alertar o usuário que tem campos vazios
	}
    }

    // Metodo que cria a questão Difícil
    public void criarQuestaoDificil() {
	if ((perguntaTxt.getText().length() > 0) && (resposta.getText().length() > 0)
		&& (dicaTxt.getText().length() > 0) && (panelAltern.getAlternativa(0).length() > 0)
		&& (panelAltern.getAlternativa(1).length() > 0) && (panelAltern.getAlternativa(2).length() > 0)
		&& (panelAltern.getAlternativa(3).length() > 0) && (selecionadoModel.getSize() > 0)) {
	    questao.setPergunta(perguntaTxt.getText());
	    questao.setResposta(resposta.getText());
	    questao.setDica(dicaTxt.getText());
	    questao.setAlternativa(panelAltern.getAlternativa(0));
	    questao.setAlternativa2(panelAltern.getAlternativa(1));
	    questao.setAlternativa3(panelAltern.getAlternativa(2));
	    questao.setAlternativa4(panelAltern.getAlternativa(3));
	    questao.setDificuldade(Dificuldade.DIFICIL);
	} else {

	    JOptionPane.showMessageDialog(FormPerguntas.this,
		    "Você esqueceu de preencher/selecionar um campo.\nVerifique e tente novamente!", AppFrame.TITULO,
		    JOptionPane.ERROR_MESSAGE);// mensagem de erro par alertar o usuário que tem campos vazios
	}
    }

    private void cancelBtn() {
	cancelarBtn = new JButton("Cancelar");
	cancelarBtn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		frame.mostrarPerguntas();// volta para a lista de Perguntas
	    }
	});
    }

    private void addComponente(JComponent rotulo, int linha, int coluna) {

	addComponente(rotulo, linha, coluna, 1, 1);
    }

    private void addComponente(JComponent componente, int linha, int coluna, int largura, int altura) {
	constraints.gridx = coluna;
	constraints.gridy = linha;
	constraints.gridwidth = largura;
	constraints.gridheight = altura;

	constraints.fill = GridBagConstraints.HORIZONTAL;
	constraints.insets = FIELD_INSETS;

	layout.setConstraints(componente, constraints);

	add(componente);

    }

    private void colocarRadio() {
	JPanel painelRadio = new JPanel();

	facil = new JRadioButton("Fácil", false);
	facil.setActionCommand("Fácil");
	facil.addItemListener(new ButtonHandler());
	medio = new JRadioButton("Médio", false);
	medio.setActionCommand("Médio");
	medio.addItemListener(new ButtonHandler());
	dificil = new JRadioButton("Difícil", false);
	dificil.setActionCommand("Difícil");
	dificil.addItemListener(new ButtonHandler());
	facil.setBackground(new Color(0, 159, 136));
	facil.setForeground(Color.WHITE);
	medio.setBackground(new Color(0, 159, 136));
	medio.setForeground(Color.WHITE);
	dificil.setBackground(new Color(0, 159, 136));
	dificil.setForeground(Color.WHITE);
	grupo1 = new ButtonGroup();
	grupo1.add(facil);
	grupo1.add(medio);
	grupo1.add(dificil);

	painelRadio.add(facil);
	painelRadio.add(medio);
	painelRadio.add(dificil);
	painelRadio.setBackground(new Color(0, 159, 136));
	addComponente(painelRadio, 4, 1);

    }

    private class ButtonHandler implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
	    if (facil.isSelected()) {
		alterCards();
		layoutAltern.show(cardsPanelAltern, FacilQuestaoPanel.class.getName());
	    } else if (medio.isSelected()) {
		alterCards();
		layoutAltern.show(cardsPanelAltern, MediaQuestaoPanel.class.getName());
	    } else if (dificil.isSelected()) {
		alterCards();
		layoutAltern.show(cardsPanelAltern, DificilQuestaoPanel.class.getName());
	    }
	}
    }

}