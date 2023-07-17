package backend.eval;

public class SampleRun {
   protected String name;
   protected boolean enabled;
   protected boolean exceptionThrown;

   public SampleRun(String name, boolean enabled) {
      this.name = name;
      this.enabled = enabled;
      this.exceptionThrown = false;
   }

   public void pre() {
   }

   public void post() {
   }

   public String getName() {
      return this.name;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean hasExceptionThrown() {
      return this.exceptionThrown;
   }
}
