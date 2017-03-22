package br.edu.ufcg.computacao.si1.model.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

public class AnuncioFilterForm {
	
	private long idUsuario;
	private boolean showOwned;
	
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date fromDate;
	
	@DateTimeFormat(pattern = "dd-mm-yyyy ")
	private Date toDate;
	
	@NotNull
    @NotEmpty
	private String type;
	 
	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean shouldShowOwned() {
		return showOwned;
	}

	public void setShowOwned(boolean showOwned) {
		this.showOwned = showOwned;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
