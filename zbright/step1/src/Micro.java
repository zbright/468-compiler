import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Micro {
  public static void main(String[] args) throws Exception {
    MicroLexer lexer = new MicroLexer(new ANTLRFileStream(args[0]));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    tokens.fill();
    for(Token token : tokens.getTokens())
    {
      try {
        System.out.println("Token Type: " + MicroLexer.tokenNames[token.getType()] + "\r");
        System.out.println("Value: " + token.getText() + "\r");
      } catch(IndexOutOfBoundsException e) {
        //Catches a failed lookup of tokenNames for last token
      }
    }
  }
}
