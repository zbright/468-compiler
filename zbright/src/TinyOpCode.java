public enum TinyOpCode {
	ADDI,
	ADDR,
	SUBI,
	SUBR,
	MULI,
	MULR,
	DIVI,
	DIVR,
	MOVE,
	SYS_WRITES,
	SYS_WRITEI,
	SYS_WRITER,
	SYS_READI,
	SYS_READR;

	public String toString() {
		return name().replace('_', ' ').toLowerCase();
	}
}