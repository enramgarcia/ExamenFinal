package com.atechsa.models;

public class Movimiento {
	
	protected String tipo;
	protected double monto;
	protected double saldo;
	protected double saldoFinal;
	
	public Movimiento(String tipo, double saldo, double monto, double saldoFinal) {
		this.tipo = tipo;
		this.monto = monto;
		this.saldo = saldo;
		this.saldoFinal = saldoFinal;
	}
	
	public String toString() {
		return String.format(
				"%s\t\t$%.2f\t\t$%.2f\t\t$%.2f", tipo, saldo, monto, saldoFinal
				);
	}
}
