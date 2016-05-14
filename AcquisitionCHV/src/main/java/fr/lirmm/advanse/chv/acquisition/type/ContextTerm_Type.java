
/* First created by JCasGen Fri May 13 18:04:20 CEST 2016 */
package fr.lirmm.advanse.chv.acquisition.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** A term (ngram) that should be taken in account in context computation
 * Updated by JCasGen Sat May 14 01:04:06 CEST 2016
 * @generated */
public class ContextTerm_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ContextTerm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ContextTerm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ContextTerm(addr, ContextTerm_Type.this);
  			   ContextTerm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ContextTerm(addr, ContextTerm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ContextTerm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.lirmm.advanse.chv.acquisition.type.ContextTerm");
 
  /** @generated */
  final Feature casFeat_NormalizedForm;
  /** @generated */
  final int     casFeatCode_NormalizedForm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNormalizedForm(int addr) {
        if (featOkTst && casFeat_NormalizedForm == null)
      jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.chv.acquisition.type.ContextTerm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_NormalizedForm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalizedForm(int addr, String v) {
        if (featOkTst && casFeat_NormalizedForm == null)
      jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.chv.acquisition.type.ContextTerm");
    ll_cas.ll_setStringValue(addr, casFeatCode_NormalizedForm, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ContextTerm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_NormalizedForm = jcas.getRequiredFeatureDE(casType, "NormalizedForm", "uima.cas.String", featOkTst);
    casFeatCode_NormalizedForm  = (null == casFeat_NormalizedForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NormalizedForm).getCode();

  }
}



    