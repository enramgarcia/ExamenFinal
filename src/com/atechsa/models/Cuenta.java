package com.atechsa.models;

import java.util.ArrayList;

public class Cuenta {
	
	protected double saldo;
	protected double saldoInicial;
	
	protected ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
	
	public Cuenta(double saldoInicial) {
		agregar(saldoInicial);
	}
	
	/**
	 * Setter saldo
	 * 
	 * @param saldo
	 * @return void
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	/**
	 * Getter saldo.
	 * 
	 * @param null
	 * @return double
	 */
	public double getSaldo() {
		return this.saldo;
	}
	
	/**
	 * Agrega monto al saldo de la cuenta y agrega la transacción a los movimientos de la cuenta.
	 * 
	 * @param monto
	 * @return void
	 */
	public void agregar(double monto) {
		double inicial = this.saldo;
		this.saldo += monto;
		
		movimientos.add(new Movimiento("Ingreso", inicial, monto, this.saldo));
	}
	
	/**
	 * Reduce el saldo de la cuenta y agrega la transacción a los movimientos de la cuenta.
	 * 
	 * @param monto
	 * @return void
	 */
	public void reducir(double monto) {
		double inicial = this.saldo;
		this.saldo -= monto;
		
		movimientos.add(new Movimiento("Egreso", inicial, monto, this.saldo));
	}
	
	/**
	 * Agrega un prestamo al listado de movimientos de la cuenta.
	 * 
	 * @param monto
	 * @return void
	 */
	public void agregarPrestamo(double monto) {
		movimientos.add(new Movimiento("Prestamo", this.saldo, monto, this.saldo));
	}
	
	/**
	 * Muestra el listado de todos los movimientos de la cuenta.
	 * 
	 * @param null
	 * @return void
	 */
	public void mostrarMovimientos() {
		System.out.println(String.format("Saldo Inicial: $%.2f\n", saldoInicial));
		
		System.out.println("Tipo\t\tSaldo\t\tMonto\t\tSaldo Final");
		
		for (Movimiento movimiento : movimientos) {
			System.out.println(movimiento);
		}

		System.out.println(String.format("\nSaldo Final: $%.2f\n", this.saldo));
	}
}
