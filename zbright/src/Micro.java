import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Micro {
  public static void main(String[] args) throws Exception {
    MicroLexer lexer = new MicroLexer(new ANTLRFileStream(args[0]));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MicroParser parser = new MicroParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());

    try {
      ParseTree tree = parser.program();
      System.out.println("Accepted");
      ParseTreeWalker walker = new ParseTreeWalker();
      MicroExtendedListener listener = new MicroExtendedListener();
      walker.walk(listener, tree);
      listener.print_symbols();
    } catch (ParseCancellationException e) {
      System.out.println("Not accepted");
    }
  }
}
