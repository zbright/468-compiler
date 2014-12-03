public class RegCounter {
  public static int regCount = 4;
  public static Register[] registers = new Register[regCount];

  public static String getNext(String newTag) {
    int oldestClean = -1;
    int oldestDirty = -1;

    for(int i = 0; i < regCount; i++) {
      if(registers[i].tag == newTag) {
        oldestClean = i;
        break;
      }

      if(oldestDirty == -1 && registers[i].dirty)
        oldestDirty = i;
      else if(oldestClean == -1 && !registers[i].dirty) 
        oldestClean = i;
      else if(!registers[i].dirty && registers[i].age > registers[oldestClean].age)
        oldestClean = i;
      else if(registers[i].dirty && registers[i].age > registers[oldestDirty].age)
        oldestDirty = i;
    }

    int nextReg = 0;
    if(oldestClean == -1) {
      nextReg = oldestDirty;
      System.out.println("move r" + nextReg + " " + registers[nextReg].tag);
    } 
    else {
      nextReg = oldestClean;
    }

    registers[nextReg].tag = newTag;
    registers[nextReg].dirty = false;
    registers[nextReg].age = 0;
    
    for(int i = 0; i < regCount; i++) {
      if(i == nextReg)
        continue;
      registers[i].age++;
    }

    return "r" + String.valueOf(nextReg);
  }
  
  public static void reset() {
    for(int i = 0; i < regCount; i++) {
      registers[i] = new Register();
    }
  }

  public static void makeDirty(String reg) {
    String regNum = reg.substring(1, reg.length());
    int regInt = Integer.parseInt(regNum);
    registers[regInt].dirty = true;

    for(int i = 0; i < regCount; i++) {
      if(i == regInt)
        continue;
      registers[i].age++;
    }
  }

  public static void makeClean(String reg) {
    String regNum = reg.substring(1, reg.length());
    int regInt = Integer.parseInt(regNum);
    registers[regInt].dirty = false;
  }

  public static boolean validate(String strReg, String tag) {
    String regNum = strReg.substring(1, strReg.length());
    int regInt = Integer.parseInt(regNum);

    return tag.equals(registers[regInt].tag);
  }
  
  // private int findOldest() {
  //   int oldestClean = -1;
  //   int oldestDirty = -1;

  //   for(int i = 0; i < _regCount; i++) {
  //     if(oldestDirty == -1 && registers[i].dirty)
  //       oldestDirty = i;
  //     else if(oldestClean == -1 && !registers[i].dirty) 
  //       oldestClean = i;
  //     else if(!registers[i].dirty && registers[i].age > registers[oldestClean].age)
  //       oldestClean = i;
  //     else if(registers[i].dirty && registers[i].age > registers[oldestDirty].age)
  //       oldestDirty = i;
  //   }

  //   return oldestClean != -1 ? oldestClean : oldestDirty; 
  // }
}