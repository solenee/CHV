

/* First created by JCasGen Fri May 13 18:04:20 CEST 2016 */
package fr.lirmm.advanse.chv.acquisition.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A term (ngram) that should be taken in account in context computation
 * Updated by JCasGen Sat May 14 01:04:06 CEST 2016
 * XML source: /home/monordi/CHV/AcquisitionCHV/src/main/resources/desc/type/Acquisition.xml
 * @generated */
public class ContextTerm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ContextTerm.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ContextTerm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ContextTerm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ContextTerm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ContextTerm(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: NormalizedForm

  /** getter for NormalizedForm - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNormalizedForm() {
    if (ContextTerm_Type.featOkTst && ((ContextTerm_Type)jcasType).casFeat_NormalizedForm == null)
      jcasType.jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.chv.acquisition.type.ContextTerm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ContextTerm_Type)jcasType).casFeatCode_NormalizedForm);}
    
  /** setter for NormalizedForm - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalizedForm(String v) {
    if (ContextTerm_Type.featOkTst && ((ContextTerm_Type)jcasType).casFeat_NormalizedForm == null)
      jcasType.jcas.throwFeatMissing("NormalizedForm", "fr.lirmm.advanse.chv.acquisition.type.ContextTerm");
    jcasType.ll_cas.ll_setStringValue(addr, ((ContextTerm_Type)jcasType).casFeatCode_NormalizedForm, v);}    
  }

    