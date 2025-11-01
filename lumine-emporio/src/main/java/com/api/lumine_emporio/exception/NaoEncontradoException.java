package com.api.lumine_emporio.exception;

public class NaoEncontradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NaoEncontradoException(String message) {
		super(message);
	}
}
