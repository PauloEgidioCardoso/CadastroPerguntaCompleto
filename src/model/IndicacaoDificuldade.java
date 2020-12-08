package model;

public class IndicacaoDificuldade {
private int id;
private String serie;

public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public String getSerie() {
    return serie;
}
public void setSerie(String serie) {
    this.serie = serie;
}

@Override
    public String toString() {
	return serie;
    }

@Override
public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	IndicacaoDificuldade indicacao = (IndicacaoDificuldade) obj;
	return id == indicacao.id;
}
} // fim da classe IndicacaoDificuldade
