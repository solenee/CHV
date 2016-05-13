
/* First created by JCasGen Thu May 12 14:41:37 CEST 2016 */
package fr.lirmm.advanse.acquisition.type;

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

/** A potential BioMedical entity, regardless of the expertise level
 * Updated by JCasGen Fri May 13 18:04:20 CEST 2016
 * @generated */
public class BioEntity_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (BioEntity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = BioEntity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new BioEntity(addr, BioEntity_Type.this);
  			   BioEntity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new BioEntity(addr, BioEntity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = BioEntity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("fr.lirmm.advanse.acquisition.type.BioEntity");



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
      jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.acquisition.type.BioEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_NormalizedForm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalizedForm(int addr, String v) {
        if (featOkTst && casFeat_NormalizedForm == null)
      jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.acquisition.type.BioEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_NormalizedForm, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public BioEntity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_NormalizedForm = jcas.getRequiredFeatureDE(casType, "NormalizedForm", "uima.cas.String", featOkTst);
    casFeatCode_NormalizedForm  = (null == casFeat_NormalizedForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NormalizedForm).getCode();

  }
}



    