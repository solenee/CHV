

/* First created by JCasGen Thu May 12 15:02:38 CEST 2016 */
package fr.lirmm.advanse.acquisition.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Model a single post on a forum
 * Updated by JCasGen Fri May 13 18:04:20 CEST 2016
 * XML source: /home/monordi/stage-lirmm/AcquisitionCHV/src/main/resources/desc/type/Acquisition.xml
 * @generated */
public class Post extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Post.class);
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
  protected Post() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Post(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Post(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Post(JCas jcas, int begin, int end) {
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
  //* Feature: SubForum

  /** getter for SubForum - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSubForum() {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_SubForum == null)
      jcasType.jcas.throwFeatMissing("SubForum", "fr.lirmm.advanse.acquisition.type.Post");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Post_Type)jcasType).casFeatCode_SubForum);}
    
  /** setter for SubForum - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSubForum(String v) {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_SubForum == null)
      jcasType.jcas.throwFeatMissing("SubForum", "fr.lirmm.advanse.acquisition.type.Post");
    jcasType.ll_cas.ll_setStringValue(addr, ((Post_Type)jcasType).casFeatCode_SubForum, v);}    
   
    
  //*--------------*
  //* Feature: Author

  /** getter for Author - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAuthor() {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Author == null)
      jcasType.jcas.throwFeatMissing("Author", "fr.lirmm.advanse.acquisition.type.Post");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Post_Type)jcasType).casFeatCode_Author);}
    
  /** setter for Author - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAuthor(String v) {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Author == null)
      jcasType.jcas.throwFeatMissing("Author", "fr.lirmm.advanse.acquisition.type.Post");
    jcasType.ll_cas.ll_setStringValue(addr, ((Post_Type)jcasType).casFeatCode_Author, v);}    
   
    
  //*--------------*
  //* Feature: Id

  /** getter for Id - gets 
   * @generated
   * @return value of the feature 
   */
  public long getId() {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "fr.lirmm.advanse.acquisition.type.Post");
    return jcasType.ll_cas.ll_getLongValue(addr, ((Post_Type)jcasType).casFeatCode_Id);}
    
  /** setter for Id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(long v) {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "fr.lirmm.advanse.acquisition.type.Post");
    jcasType.ll_cas.ll_setLongValue(addr, ((Post_Type)jcasType).casFeatCode_Id, v);}    
   
    
  //*--------------*
  //* Feature: Thread

  /** getter for Thread - gets 
   * @generated
   * @return value of the feature 
   */
  public String getThread() {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Thread == null)
      jcasType.jcas.throwFeatMissing("Thread", "fr.lirmm.advanse.acquisition.type.Post");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Post_Type)jcasType).casFeatCode_Thread);}
    
  /** setter for Thread - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setThread(String v) {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Thread == null)
      jcasType.jcas.throwFeatMissing("Thread", "fr.lirmm.advanse.acquisition.type.Post");
    jcasType.ll_cas.ll_setStringValue(addr, ((Post_Type)jcasType).casFeatCode_Thread, v);}    
   
    
  //*--------------*
  //* Feature: Title

  /** getter for Title - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTitle() {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Title == null)
      jcasType.jcas.throwFeatMissing("Title", "fr.lirmm.advanse.acquisition.type.Post");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Post_Type)jcasType).casFeatCode_Title);}
    
  /** setter for Title - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTitle(String v) {
    if (Post_Type.featOkTst && ((Post_Type)jcasType).casFeat_Title == null)
      jcasType.jcas.throwFeatMissing("Title", "fr.lirmm.advanse.acquisition.type.Post");
    jcasType.ll_cas.ll_setStringValue(addr, ((Post_Type)jcasType).casFeatCode_Title, v);}    
  }

    