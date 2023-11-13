<?xml version="1.0" encoding="UTF-8"?>
<model ref="r:4b14c71b-877d-4be6-bd4a-3428cfcc870d(xjsnark.tls13_key_schedules)">
  <persistence version="9" />
  <languages>
    <use id="0688d542-e2a3-492c-a31f-0e921fd6a8fb" name="xjsnark" version="0" />
    <use id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage" version="4" />
  </languages>
  <imports>
    <import index="d2b1" ref="r:805ab20b-1963-4bd7-ae02-dfb3fada1182(xjsnark.util_and_sha)" />
    <import index="2v2w" ref="r:4b14c71b-877d-4be6-bd4a-3428cfcc870d(xjsnark.tls13_key_schedules)" />
    <import index="liel" ref="r:1e4e7e47-5204-4166-a233-48cf8c81db83(xjsnark.aes_gcm)" />
    <import index="rktg" ref="r:6c3df212-d5bc-41ad-b103-b7c850c4b753(xjsnark.ecdhe)" />
    <import index="tluv" ref="r:497ff602-8d96-4239-8b0f-254445ada898(xjsnark.field_table)" />
    <import index="wyt6" ref="6354ebe7-c22a-4a0f-ac54-50b52ab9b065/java:java.lang(JDK/)" implicit="true" />
  </imports>
  <registry>
    <language id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage">
      <concept id="1215693861676" name="jetbrains.mps.baseLanguage.structure.BaseAssignmentExpression" flags="nn" index="d038R">
        <child id="1068498886297" name="rValue" index="37vLTx" />
        <child id="1068498886295" name="lValue" index="37vLTJ" />
      </concept>
      <concept id="1153422105332" name="jetbrains.mps.baseLanguage.structure.RemExpression" flags="nn" index="2dk9JS" />
      <concept id="1202948039474" name="jetbrains.mps.baseLanguage.structure.InstanceMethodCallOperation" flags="nn" index="liA8E" />
      <concept id="1179360813171" name="jetbrains.mps.baseLanguage.structure.HexIntegerLiteral" flags="nn" index="2nou5x">
        <property id="1179360856892" name="value" index="2noCCI" />
      </concept>
      <concept id="1224500799915" name="jetbrains.mps.baseLanguage.structure.BitwiseXorExpression" flags="nn" index="pVQyQ" />
      <concept id="1465982738277781862" name="jetbrains.mps.baseLanguage.structure.PlaceholderMember" flags="ng" index="2tJIrI" />
      <concept id="1239714755177" name="jetbrains.mps.baseLanguage.structure.AbstractUnaryNumberOperation" flags="nn" index="2$Kvd9">
        <child id="1239714902950" name="expression" index="2$L3a6" />
      </concept>
      <concept id="1173175405605" name="jetbrains.mps.baseLanguage.structure.ArrayAccessExpression" flags="nn" index="AH0OO">
        <child id="1173175577737" name="index" index="AHEQo" />
        <child id="1173175590490" name="array" index="AHHXb" />
      </concept>
      <concept id="1188220165133" name="jetbrains.mps.baseLanguage.structure.ArrayLiteral" flags="nn" index="2BsdOp">
        <child id="1188220173759" name="item" index="2BsfMF" />
      </concept>
      <concept id="1095950406618" name="jetbrains.mps.baseLanguage.structure.DivExpression" flags="nn" index="FJ1c_" />
      <concept id="1154032098014" name="jetbrains.mps.baseLanguage.structure.AbstractLoopStatement" flags="nn" index="2LF5Ji">
        <child id="1154032183016" name="body" index="2LFqv$" />
      </concept>
      <concept id="1197027756228" name="jetbrains.mps.baseLanguage.structure.DotExpression" flags="nn" index="2OqwBi">
        <child id="1197027771414" name="operand" index="2Oq$k0" />
        <child id="1197027833540" name="operation" index="2OqNvi" />
      </concept>
      <concept id="1145552977093" name="jetbrains.mps.baseLanguage.structure.GenericNewExpression" flags="nn" index="2ShNRf">
        <child id="1145553007750" name="creator" index="2ShVmc" />
      </concept>
      <concept id="5205855332950442198" name="jetbrains.mps.baseLanguage.structure.ArrayCloneOperation" flags="nn" index="2SEQ$1" />
      <concept id="1070462154015" name="jetbrains.mps.baseLanguage.structure.StaticFieldDeclaration" flags="ig" index="Wx3nA">
        <property id="6468716278899126575" name="isVolatile" index="2dlcS1" />
        <property id="6468716278899125786" name="isTransient" index="2dld4O" />
      </concept>
      <concept id="1070475926800" name="jetbrains.mps.baseLanguage.structure.StringLiteral" flags="nn" index="Xl_RD">
        <property id="1070475926801" name="value" index="Xl_RC" />
      </concept>
      <concept id="1081236700938" name="jetbrains.mps.baseLanguage.structure.StaticMethodDeclaration" flags="ig" index="2YIFZL" />
      <concept id="1081236700937" name="jetbrains.mps.baseLanguage.structure.StaticMethodCall" flags="nn" index="2YIFZM">
        <reference id="1144433194310" name="classConcept" index="1Pybhc" />
      </concept>
      <concept id="1070534370425" name="jetbrains.mps.baseLanguage.structure.IntegerType" flags="in" index="10Oyi0" />
      <concept id="1070534760951" name="jetbrains.mps.baseLanguage.structure.ArrayType" flags="in" index="10Q1$e">
        <child id="1070534760952" name="componentType" index="10Q1$1" />
      </concept>
      <concept id="1068390468198" name="jetbrains.mps.baseLanguage.structure.ClassConcept" flags="ig" index="312cEu" />
      <concept id="1068431474542" name="jetbrains.mps.baseLanguage.structure.VariableDeclaration" flags="ng" index="33uBYm">
        <property id="1176718929932" name="isFinal" index="3TUv4t" />
        <child id="1068431790190" name="initializer" index="33vP2m" />
      </concept>
      <concept id="1092119917967" name="jetbrains.mps.baseLanguage.structure.MulExpression" flags="nn" index="17qRlL" />
      <concept id="1068498886296" name="jetbrains.mps.baseLanguage.structure.VariableReference" flags="nn" index="37vLTw">
        <reference id="1068581517664" name="variableDeclaration" index="3cqZAo" />
      </concept>
      <concept id="1068498886292" name="jetbrains.mps.baseLanguage.structure.ParameterDeclaration" flags="ir" index="37vLTG" />
      <concept id="1068498886294" name="jetbrains.mps.baseLanguage.structure.AssignmentExpression" flags="nn" index="37vLTI" />
      <concept id="1225271177708" name="jetbrains.mps.baseLanguage.structure.StringType" flags="in" index="17QB3L" />
      <concept id="4972933694980447171" name="jetbrains.mps.baseLanguage.structure.BaseVariableDeclaration" flags="ng" index="19Szcq">
        <child id="5680397130376446158" name="type" index="1tU5fm" />
      </concept>
      <concept id="1068580123132" name="jetbrains.mps.baseLanguage.structure.BaseMethodDeclaration" flags="ng" index="3clF44">
        <property id="4276006055363816570" name="isSynchronized" index="od$2w" />
        <property id="1181808852946" name="isFinal" index="DiZV1" />
        <child id="1068580123133" name="returnType" index="3clF45" />
        <child id="1068580123134" name="parameter" index="3clF46" />
        <child id="1068580123135" name="body" index="3clF47" />
      </concept>
      <concept id="1068580123155" name="jetbrains.mps.baseLanguage.structure.ExpressionStatement" flags="nn" index="3clFbF">
        <child id="1068580123156" name="expression" index="3clFbG" />
      </concept>
      <concept id="1068580123157" name="jetbrains.mps.baseLanguage.structure.Statement" flags="nn" index="3clFbH" />
      <concept id="1068580123159" name="jetbrains.mps.baseLanguage.structure.IfStatement" flags="nn" index="3clFbJ">
        <child id="1068580123160" name="condition" index="3clFbw" />
        <child id="1068580123161" name="ifTrue" index="3clFbx" />
        <child id="1206060520071" name="elsifClauses" index="3eNLev" />
      </concept>
      <concept id="1068580123136" name="jetbrains.mps.baseLanguage.structure.StatementList" flags="sn" stub="5293379017992965193" index="3clFbS">
        <child id="1068581517665" name="statement" index="3cqZAp" />
      </concept>
      <concept id="1068580320020" name="jetbrains.mps.baseLanguage.structure.IntegerConstant" flags="nn" index="3cmrfG">
        <property id="1068580320021" name="value" index="3cmrfH" />
      </concept>
      <concept id="1068581242875" name="jetbrains.mps.baseLanguage.structure.PlusExpression" flags="nn" index="3cpWs3" />
      <concept id="1068581242878" name="jetbrains.mps.baseLanguage.structure.ReturnStatement" flags="nn" index="3cpWs6">
        <child id="1068581517676" name="expression" index="3cqZAk" />
      </concept>
      <concept id="1068581242864" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclarationStatement" flags="nn" index="3cpWs8">
        <child id="1068581242865" name="localVariableDeclaration" index="3cpWs9" />
      </concept>
      <concept id="1068581242869" name="jetbrains.mps.baseLanguage.structure.MinusExpression" flags="nn" index="3cpWsd" />
      <concept id="1068581242863" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclaration" flags="nr" index="3cpWsn" />
      <concept id="1206060495898" name="jetbrains.mps.baseLanguage.structure.ElsifClause" flags="ng" index="3eNFk2">
        <child id="1206060619838" name="condition" index="3eO9$A" />
        <child id="1206060644605" name="statementList" index="3eOfB_" />
      </concept>
      <concept id="1081506762703" name="jetbrains.mps.baseLanguage.structure.GreaterThanExpression" flags="nn" index="3eOSWO" />
      <concept id="1081506773034" name="jetbrains.mps.baseLanguage.structure.LessThanExpression" flags="nn" index="3eOVzh" />
      <concept id="1154542696413" name="jetbrains.mps.baseLanguage.structure.ArrayCreatorWithInitializer" flags="nn" index="3g6Rrh">
        <child id="1154542793668" name="componentType" index="3g7fb8" />
        <child id="1154542803372" name="initValue" index="3g7hyw" />
      </concept>
      <concept id="1204053956946" name="jetbrains.mps.baseLanguage.structure.IMethodCall" flags="ng" index="1ndlxa">
        <reference id="1068499141037" name="baseMethodDeclaration" index="37wK5l" />
        <child id="1068499141038" name="actualArgument" index="37wK5m" />
      </concept>
      <concept id="1107461130800" name="jetbrains.mps.baseLanguage.structure.Classifier" flags="ng" index="3pOWGL">
        <child id="5375687026011219971" name="member" index="jymVt" unordered="true" />
      </concept>
      <concept id="7812454656619025416" name="jetbrains.mps.baseLanguage.structure.MethodDeclaration" flags="ng" index="1rXfSm">
        <property id="8355037393041754995" name="isNative" index="2aFKle" />
      </concept>
      <concept id="7812454656619025412" name="jetbrains.mps.baseLanguage.structure.LocalMethodCall" flags="nn" index="1rXfSq" />
      <concept id="1081773326031" name="jetbrains.mps.baseLanguage.structure.BinaryOperation" flags="nn" index="3uHJSO">
        <child id="1081773367579" name="rightExpression" index="3uHU7w" />
        <child id="1081773367580" name="leftExpression" index="3uHU7B" />
      </concept>
      <concept id="1214918800624" name="jetbrains.mps.baseLanguage.structure.PostfixIncrementExpression" flags="nn" index="3uNrnE" />
      <concept id="1184950988562" name="jetbrains.mps.baseLanguage.structure.ArrayCreator" flags="nn" index="3$_iS1">
        <child id="1184951007469" name="componentType" index="3$_nBY" />
        <child id="1184952969026" name="dimensionExpression" index="3$GQph" />
      </concept>
      <concept id="1184952934362" name="jetbrains.mps.baseLanguage.structure.DimensionExpression" flags="nn" index="3$GHV9">
        <child id="1184953288404" name="expression" index="3$I4v7" />
      </concept>
      <concept id="1178549954367" name="jetbrains.mps.baseLanguage.structure.IVisible" flags="ng" index="1B3ioH">
        <child id="1178549979242" name="visibility" index="1B3o_S" />
      </concept>
      <concept id="1144230876926" name="jetbrains.mps.baseLanguage.structure.AbstractForStatement" flags="nn" index="1DupvO">
        <child id="1144230900587" name="variable" index="1Duv9x" />
      </concept>
      <concept id="1144231330558" name="jetbrains.mps.baseLanguage.structure.ForStatement" flags="nn" index="1Dw8fO">
        <child id="1144231399730" name="condition" index="1Dwp0S" />
        <child id="1144231408325" name="iteration" index="1Dwrff" />
      </concept>
      <concept id="1225892319711" name="jetbrains.mps.baseLanguage.structure.ShiftRightExpression" flags="nn" index="1GS532" />
      <concept id="1208890769693" name="jetbrains.mps.baseLanguage.structure.ArrayLengthOperation" flags="nn" index="1Rwk04" />
      <concept id="6329021646629104957" name="jetbrains.mps.baseLanguage.structure.TextCommentPart" flags="nn" index="3SKdUq">
        <property id="6329021646629104958" name="text" index="3SKdUp" />
      </concept>
      <concept id="6329021646629104954" name="jetbrains.mps.baseLanguage.structure.SingleLineComment" flags="nn" index="3SKdUt">
        <child id="6329021646629175155" name="commentPart" index="3SKWNk" />
      </concept>
      <concept id="1146644602865" name="jetbrains.mps.baseLanguage.structure.PublicVisibility" flags="nn" index="3Tm1VV" />
    </language>
    <language id="0688d542-e2a3-492c-a31f-0e921fd6a8fb" name="xjsnark">
      <concept id="1307139697235026701" name="xjsnark.structure.LogStatement" flags="ng" index="vCCuG">
        <child id="1307139697235029218" name="msg" index="vCCx3" />
        <child id="1307139697235028892" name="expression" index="vCCWX" />
      </concept>
      <concept id="7553992366106506034" name="xjsnark.structure.JFieldType" flags="ig" index="2D7PWU">
        <reference id="7553992366106506060" name="fieldRec" index="2D7PX4" />
      </concept>
      <concept id="7495353643616961541" name="xjsnark.structure.SingleLineCommentClassMember" flags="ng" index="DJdLC">
        <property id="7495353643619293787" name="text" index="DRO8Q" />
      </concept>
      <concept id="2280652580002804071" name="xjsnark.structure.MemoryAccessExpression" flags="ng" index="SwV0n">
        <child id="2280652580002804074" name="index" index="SwV0q" />
        <child id="2280652580002804076" name="memory" index="SwV0s" />
      </concept>
      <concept id="2280652580001293749" name="xjsnark.structure.MemoryType" flags="ig" index="SEaj5">
        <child id="2280652580001293765" name="type" index="SEaiP" />
      </concept>
      <concept id="2280652580001293832" name="xjsnark.structure.InitMemory" flags="ng" index="SEatS">
        <child id="1929320883460933539" name="type" index="6EdiW" />
        <child id="2280652580001293834" name="argArray" index="SEatU" />
      </concept>
      <concept id="4165393367774947854" name="xjsnark.structure.JUnsignedIntegerType" flags="ig" index="3qc1$W">
        <property id="4165393367774948449" name="bitwidth" index="3qc1Xj" />
      </concept>
      <concept id="8340315132972716924" name="xjsnark.structure.VerifyEqStatement" flags="ng" index="3s6pcg">
        <child id="8340315132972716925" name="exp1" index="3s6pch" />
        <child id="8340315132972716926" name="exp2" index="3s6pci" />
      </concept>
      <concept id="9096502420330357553" name="xjsnark.structure.JUnsignedIntegerConversion" flags="ng" index="3SuevK">
        <child id="9096502420330357585" name="argument" index="3Sueug" />
        <child id="9096502420330357558" name="jType" index="3SuevR" />
      </concept>
    </language>
    <language id="ceab5195-25ea-4f22-9b92-103b95ca8c0c" name="jetbrains.mps.lang.core">
      <concept id="1133920641626" name="jetbrains.mps.lang.core.structure.BaseConcept" flags="ng" index="2VYdi">
        <child id="5169995583184591170" name="smodelAttribute" index="lGtFl" />
      </concept>
      <concept id="1169194658468" name="jetbrains.mps.lang.core.structure.INamedConcept" flags="ng" index="TrEIO">
        <property id="1169194664001" name="name" index="TrG5h" />
      </concept>
      <concept id="709746936026466394" name="jetbrains.mps.lang.core.structure.ChildAttribute" flags="ng" index="3VBwX9">
        <property id="709746936026609031" name="linkId" index="3V$3ak" />
        <property id="709746936026609029" name="linkRole" index="3V$3am" />
      </concept>
      <concept id="4452961908202556907" name="jetbrains.mps.lang.core.structure.BaseCommentAttribute" flags="ng" index="1X3_iC">
        <child id="3078666699043039389" name="commentedNode" index="8Wnug" />
      </concept>
    </language>
  </registry>
  <node concept="312cEu" id="2OJAT4$Z6ed">
    <property role="TrG5h" value="HKDF" />
    <node concept="2tJIrI" id="1fN2f79U_Fo" role="jymVt" />
    <node concept="DJdLC" id="1fN2f79U_Pv" role="jymVt">
      <property role="DRO8Q" value="This file implements both HMAC and HKDF (RFC 5869) with SHA256 as the base hash function." />
    </node>
    <node concept="DJdLC" id="1fN2f79UAwV" role="jymVt">
      <property role="DRO8Q" value="The three main functions to implement are:" />
    </node>
    <node concept="DJdLC" id="1fN2f79UAI1" role="jymVt">
      <property role="DRO8Q" value="(1) HMAC" />
    </node>
    <node concept="DJdLC" id="1fN2f79UAUj" role="jymVt">
      <property role="DRO8Q" value="(2) HKDF Extract " />
    </node>
    <node concept="DJdLC" id="1fN2f79UB7V" role="jymVt">
      <property role="DRO8Q" value="(3) HKDF Expand - this is a iterative function, but only one iteration is required in TLS 1.3" />
    </node>
    <node concept="DJdLC" id="1fN2f79UBjT" role="jymVt">
      <property role="DRO8Q" value="The last two call HMAC after processing their inputs." />
    </node>
    <node concept="DJdLC" id="1fN2f79UC1D" role="jymVt">
      <property role="DRO8Q" value="Furthermore, TLS 1.3 uses Expand in particular ways depending on what the desired output is (a secret, key or iv)" />
    </node>
    <node concept="DJdLC" id="1fN2f79UCs1" role="jymVt">
      <property role="DRO8Q" value="It also pre-processes the inputs in specific ways, such as prepending the string &quot;tls13 &quot; to the label" />
    </node>
    <node concept="2tJIrI" id="1fN2f79UGE8" role="jymVt" />
    <node concept="DJdLC" id="1fN2f79UJjN" role="jymVt">
      <property role="DRO8Q" value="Fixed bytes used in the HMAC function" />
    </node>
    <node concept="Wx3nA" id="1fN2f79UGYF" role="jymVt">
      <property role="2dlcS1" value="false" />
      <property role="2dld4O" value="false" />
      <property role="TrG5h" value="IPAD" />
      <property role="3TUv4t" value="true" />
      <node concept="3Tm1VV" id="1fN2f79UGP5" role="1B3o_S" />
      <node concept="10Oyi0" id="1fN2f79UGW7" role="1tU5fm" />
      <node concept="2nou5x" id="1fN2f79UH5C" role="33vP2m">
        <property role="2noCCI" value="36" />
      </node>
    </node>
    <node concept="Wx3nA" id="1fN2f79UHi5" role="jymVt">
      <property role="2dlcS1" value="false" />
      <property role="2dld4O" value="false" />
      <property role="TrG5h" value="OPAD" />
      <property role="3TUv4t" value="true" />
      <node concept="3Tm1VV" id="1fN2f79UHi6" role="1B3o_S" />
      <node concept="10Oyi0" id="1fN2f79UHi7" role="1tU5fm" />
      <node concept="2nou5x" id="1fN2f79UHi8" role="33vP2m">
        <property role="2noCCI" value="5c" />
      </node>
    </node>
    <node concept="2tJIrI" id="2OJAT4$Zc6C" role="jymVt" />
    <node concept="2tJIrI" id="1fN2f79UHbp" role="jymVt" />
    <node concept="DJdLC" id="2OJAT4hfbK4" role="jymVt">
      <property role="DRO8Q" value="HMAC function:" />
    </node>
    <node concept="DJdLC" id="1fN2f79UCTT" role="jymVt">
      <property role="DRO8Q" value="HMAC(key, salt) = H((k \xor ipad) || H((k \xor opad)  ||  salt)) " />
    </node>
    <node concept="DJdLC" id="1fN2f79UDV5" role="jymVt">
      <property role="DRO8Q" value="where ipad and opad are fixed bytes (0x36 and 0x5c respective)" />
    </node>
    <node concept="2YIFZL" id="2OJAT4_14YZ" role="jymVt">
      <property role="TrG5h" value="hmac" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_14Z1" role="3clF47">
        <node concept="3clFbH" id="2OJAT4_14Z2" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79UEyX" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79UEyZ" role="3SKWNk">
            <property role="3SKdUp" value="the key is padded to 512 bits when using SHA256" />
          </node>
        </node>
        <node concept="3clFbJ" id="2OJAT4_14Z3" role="3cqZAp">
          <node concept="3clFbS" id="2OJAT4_14Z4" role="3clFbx">
            <node concept="3cpWs8" id="2OJAT4_14Z5" role="3cqZAp">
              <node concept="3cpWsn" id="2OJAT4_14Z6" role="3cpWs9">
                <property role="TrG5h" value="key_pad" />
                <node concept="10Q1$e" id="2OJAT4_14Z7" role="1tU5fm">
                  <node concept="3qc1$W" id="2OJAT4_14Z8" role="10Q1$1">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
                <node concept="2YIFZM" id="2OJAT4_14Z9" role="33vP2m">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                  <node concept="3cpWsd" id="2OJAT4_14Za" role="37wK5m">
                    <node concept="2OqwBi" id="2OJAT4_14Zb" role="3uHU7w">
                      <node concept="37vLTw" id="2OJAT4_14Zc" role="2Oq$k0">
                        <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
                      </node>
                      <node concept="1Rwk04" id="2OJAT4_14Zd" role="2OqNvi" />
                    </node>
                    <node concept="3cmrfG" id="2OJAT4_14Ze" role="3uHU7B">
                      <property role="3cmrfH" value="64" />
                    </node>
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="2OJAT4_14Zg" role="3cqZAp">
              <node concept="37vLTI" id="2OJAT4_14Zh" role="3clFbG">
                <node concept="2YIFZM" id="2OJAT4_14Zi" role="37vLTx">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
                  <node concept="37vLTw" id="2OJAT4_14Zj" role="37wK5m">
                    <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
                  </node>
                  <node concept="37vLTw" id="2OJAT4_14Zk" role="37wK5m">
                    <ref role="3cqZAo" node="2OJAT4_14Z6" resolve="key_pad" />
                  </node>
                </node>
                <node concept="37vLTw" id="2OJAT4_14Zl" role="37vLTJ">
                  <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3eOVzh" id="2OJAT4_14Zm" role="3clFbw">
            <node concept="3cmrfG" id="2OJAT4_14Zn" role="3uHU7w">
              <property role="3cmrfH" value="64" />
            </node>
            <node concept="2OqwBi" id="2OJAT4_14Zo" role="3uHU7B">
              <node concept="37vLTw" id="2OJAT4_14Zp" role="2Oq$k0">
                <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
              </node>
              <node concept="1Rwk04" id="2OJAT4_14Zq" role="2OqNvi" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79UFaJ" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79UFU8" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79UFUa" role="3SKWNk">
            <property role="3SKdUp" value="We xor every byte of the key with ipad and opad to generate the following two strings" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_14Zr" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_14Zs" role="3cpWs9">
            <property role="TrG5h" value="key_ipad" />
            <node concept="10Q1$e" id="2OJAT4_14Zt" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_14Zu" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_14Zv" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <ref role="37wK5l" to="d2b1:2OJAT4_05yY" resolve="xor_with_byte" />
              <node concept="37vLTw" id="2OJAT4_14Zw" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
              </node>
              <node concept="3SuevK" id="2OJAT4_14Zx" role="37wK5m">
                <node concept="3qc1$W" id="2OJAT4_14Zy" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="37vLTw" id="1fN2f79UHui" role="3Sueug">
                  <ref role="3cqZAo" node="1fN2f79UGYF" resolve="IPAD" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_14Z$" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_14Z_" role="3cpWs9">
            <property role="TrG5h" value="key_opad" />
            <node concept="10Q1$e" id="2OJAT4_14ZA" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_14ZB" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_14ZC" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <ref role="37wK5l" to="d2b1:2OJAT4_05yY" resolve="xor_with_byte" />
              <node concept="37vLTw" id="2OJAT4_14ZD" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_1500" resolve="key" />
              </node>
              <node concept="3SuevK" id="2OJAT4_14ZE" role="37wK5m">
                <node concept="3qc1$W" id="2OJAT4_14ZF" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="37vLTw" id="1fN2f79UIxX" role="3Sueug">
                  <ref role="3cqZAo" node="1fN2f79UHi5" resolve="OPAD" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_14ZH" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79UJSu" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79UJSw" role="3SKWNk">
            <property role="3SKdUp" value="The inner of the two nested hashes" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_14ZJ" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_14ZK" role="3cpWs9">
            <property role="TrG5h" value="inner_hash" />
            <node concept="10Q1$e" id="2OJAT4_14ZL" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_14ZM" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_14ZN" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              <ref role="37wK5l" to="d2b1:2OJAT4$YLD7" resolve="sha2" />
              <node concept="2YIFZM" id="2OJAT4_14ZO" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
                <node concept="37vLTw" id="2OJAT4_14ZP" role="37wK5m">
                  <ref role="3cqZAo" node="2OJAT4_14Zs" resolve="key_ipad" />
                </node>
                <node concept="37vLTw" id="2OJAT4_14ZQ" role="37wK5m">
                  <ref role="3cqZAo" node="2OJAT4_1503" resolve="salt" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_14ZR" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79ULDL" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79ULDN" role="3SKWNk">
            <property role="3SKdUp" value="The outer of the two nested hashes" />
          </node>
        </node>
        <node concept="3cpWs6" id="2OJAT4_14ZS" role="3cqZAp">
          <node concept="2YIFZM" id="2OJAT4_14ZT" role="3cqZAk">
            <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
            <ref role="37wK5l" to="d2b1:2OJAT4$YLD7" resolve="sha2" />
            <node concept="2YIFZM" id="2OJAT4_14ZU" role="37wK5m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
              <node concept="37vLTw" id="2OJAT4_14ZV" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_14Z_" resolve="key_opad" />
              </node>
              <node concept="37vLTw" id="2OJAT4_14ZW" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_14ZK" resolve="inner_hash" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_14ZY" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_14ZZ" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_1500" role="3clF46">
        <property role="TrG5h" value="key" />
        <node concept="10Q1$e" id="2OJAT4_1501" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_1502" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_1503" role="3clF46">
        <property role="TrG5h" value="salt" />
        <node concept="10Q1$e" id="2OJAT4_1504" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_1505" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2OJAT4_14ZX" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2OJAT4$ZcsN" role="jymVt" />
    <node concept="2tJIrI" id="2OJAT4$Z6er" role="jymVt" />
    <node concept="DJdLC" id="2OJAT4hiLVP" role="jymVt">
      <property role="DRO8Q" value=" HKDF Extract" />
    </node>
    <node concept="2YIFZL" id="2OJAT4_15q9" role="jymVt">
      <property role="TrG5h" value="hkdf_extract" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_15qb" role="3clF47">
        <node concept="3cpWs6" id="2OJAT4_15qd" role="3cqZAp">
          <node concept="1rXfSq" id="2OJAT4_15qe" role="3cqZAk">
            <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
            <node concept="37vLTw" id="2OJAT4_15qf" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_15qk" resolve="salt" />
            </node>
            <node concept="37vLTw" id="2OJAT4_15qg" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_15qn" resolve="key" />
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_15qi" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_15qj" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_15qk" role="3clF46">
        <property role="TrG5h" value="salt" />
        <node concept="10Q1$e" id="2OJAT4_15ql" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_15qm" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_15qn" role="3clF46">
        <property role="TrG5h" value="key" />
        <node concept="10Q1$e" id="2OJAT4_15qo" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_15qp" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2OJAT4_15qh" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2OJAT4s2HOc" role="jymVt" />
    <node concept="DJdLC" id="1fN2f79UR7T" role="jymVt">
      <property role="DRO8Q" value="One iteration of HKDF expand, the one_byte being appending to the 'info' input" />
    </node>
    <node concept="2YIFZL" id="2OJAT4_16PV" role="jymVt">
      <property role="TrG5h" value="hkdf_expand" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_16PX" role="3clF47">
        <node concept="3cpWs8" id="2OJAT4_16PY" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_16PZ" role="3cpWs9">
            <property role="TrG5h" value="the_one_byte" />
            <node concept="10Q1$e" id="2OJAT4_16Q0" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_16Q1" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2BsdOp" id="2OJAT4_16Q2" role="33vP2m">
              <node concept="3SuevK" id="2OJAT4_16Q3" role="2BsfMF">
                <node concept="3qc1$W" id="2OJAT4_16Q4" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="2OJAT4_16Q5" role="3Sueug">
                  <property role="3cmrfH" value="1" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_16Q7" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_16Q8" role="3cpWs9">
            <property role="TrG5h" value="label" />
            <node concept="10Q1$e" id="2OJAT4_16Q9" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_16Qa" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_16Qb" role="33vP2m">
              <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <node concept="37vLTw" id="2OJAT4_16Qc" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16Qp" resolve="info" />
              </node>
              <node concept="37vLTw" id="2OJAT4_16Qd" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16PZ" resolve="the_one_byte" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_16Qe" role="3cqZAp" />
        <node concept="3cpWs6" id="2OJAT4_16Qf" role="3cqZAp">
          <node concept="1rXfSq" id="2OJAT4_16Qg" role="3cqZAk">
            <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
            <node concept="37vLTw" id="2OJAT4_16Qh" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_16Qm" resolve="key" />
            </node>
            <node concept="37vLTw" id="2OJAT4_16Qi" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_16Q8" resolve="label" />
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_16Qk" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_16Ql" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_16Qm" role="3clF46">
        <property role="TrG5h" value="key" />
        <node concept="10Q1$e" id="2OJAT4_16Qn" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_16Qo" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_16Qp" role="3clF46">
        <property role="TrG5h" value="info" />
        <node concept="10Q1$e" id="2OJAT4_16Qq" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_16Qr" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2OJAT4_16Qj" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="1fN2f79UU0l" role="jymVt" />
    <node concept="2tJIrI" id="1fN2f79UM8T" role="jymVt" />
    <node concept="DJdLC" id="1fN2f79UW7E" role="jymVt">
      <property role="DRO8Q" value="This function generates the label to be used by the TLS 1.3 algorithm when calling HKDF" />
    </node>
    <node concept="DJdLC" id="1fN2f79UX7w" role="jymVt">
      <property role="DRO8Q" value="The description is in RFC 8446, Section 7.1" />
    </node>
    <node concept="2YIFZL" id="2OJAT4_15E$" role="jymVt">
      <property role="TrG5h" value="get_tls_hkdf_label" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_15EA" role="3clF47">
        <node concept="3clFbH" id="1fN2f79UZwx" role="3cqZAp" />
        <node concept="3SKdUt" id="2OJAT4_15EB" role="3cqZAp">
          <node concept="3SKdUq" id="2OJAT4_15EC" role="3SKWNk">
            <property role="3SKdUp" value="Get length of the desired output represented as 2 bytes" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15EM" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15EN" role="3cpWs9">
            <property role="TrG5h" value="output_len_in_bytes" />
            <node concept="3qc1$W" id="2OJAT4_15EO" role="1tU5fm">
              <property role="3qc1Xj" value="16" />
            </node>
            <node concept="3SuevK" id="2OJAT4_15EP" role="33vP2m">
              <node concept="3qc1$W" id="2OJAT4_15EQ" role="3SuevR">
                <property role="3qc1Xj" value="16" />
              </node>
              <node concept="37vLTw" id="2OJAT4_15ER" role="3Sueug">
                <ref role="3cqZAo" node="2OJAT4_15G2" resolve="output_len" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15ES" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15ET" role="3cpWs9">
            <property role="TrG5h" value="output_len_bytes" />
            <node concept="10Q1$e" id="2OJAT4_15EU" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_15EV" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2BsdOp" id="2OJAT4_15EW" role="33vP2m">
              <node concept="3SuevK" id="2OJAT4_15EX" role="2BsfMF">
                <node concept="3qc1$W" id="2OJAT4_15EY" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="1GS532" id="2OJAT4_15EZ" role="3Sueug">
                  <node concept="3cmrfG" id="2OJAT4_15F0" role="3uHU7w">
                    <property role="3cmrfH" value="8" />
                  </node>
                  <node concept="37vLTw" id="2OJAT4_15F1" role="3uHU7B">
                    <ref role="3cqZAo" node="2OJAT4_15EN" resolve="output_len_in_bytes" />
                  </node>
                </node>
              </node>
              <node concept="3SuevK" id="2OJAT4_15F2" role="2BsfMF">
                <node concept="3qc1$W" id="2OJAT4_15F3" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="37vLTw" id="2OJAT4_15F4" role="3Sueug">
                  <ref role="3cqZAo" node="2OJAT4_15EN" resolve="output_len_in_bytes" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_15F5" role="3cqZAp" />
        <node concept="3SKdUt" id="2OJAT4_15F6" role="3cqZAp">
          <node concept="3SKdUq" id="2OJAT4_15F7" role="3SKWNk">
            <property role="3SKdUp" value="Append &quot;tls13 &quot; to the label string " />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15Fk" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15Fl" role="3cpWs9">
            <property role="TrG5h" value="label_bytes" />
            <node concept="10Q1$e" id="2OJAT4_15Fm" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_15Fn" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_15Fo" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <ref role="37wK5l" to="d2b1:2OJAT4_01cH" resolve="string_to_bytes" />
              <node concept="3cpWs3" id="2OJAT4_15Fp" role="37wK5m">
                <node concept="37vLTw" id="2OJAT4_15Fq" role="3uHU7w">
                  <ref role="3cqZAo" node="2OJAT4_15G4" resolve="label_string" />
                </node>
                <node concept="Xl_RD" id="2OJAT4_15Fr" role="3uHU7B">
                  <property role="Xl_RC" value="tls13 " />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_15Fs" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79V3kS" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79V3kU" role="3SKWNk">
            <property role="3SKdUp" value="Prepend the length of the new label represented as 1 byte" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15F8" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15F9" role="3cpWs9">
            <property role="TrG5h" value="label_len_byte" />
            <node concept="10Q1$e" id="2OJAT4_15Fa" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_15Fb" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2BsdOp" id="2OJAT4_15Fc" role="33vP2m">
              <node concept="3SuevK" id="2OJAT4_15Fd" role="2BsfMF">
                <node concept="3qc1$W" id="2OJAT4_15Fe" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cpWs3" id="2OJAT4_15Ff" role="3Sueug">
                  <node concept="2OqwBi" id="2OJAT4_15Fg" role="3uHU7w">
                    <node concept="37vLTw" id="2OJAT4_15Fh" role="2Oq$k0">
                      <ref role="3cqZAo" node="2OJAT4_15G4" resolve="label_string" />
                    </node>
                    <node concept="liA8E" id="2OJAT4_15Fi" role="2OqNvi">
                      <ref role="37wK5l" to="wyt6:~String.length():int" resolve="length" />
                    </node>
                  </node>
                  <node concept="3cmrfG" id="2OJAT4_15Fj" role="3uHU7B">
                    <property role="3cmrfH" value="6" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79V2rB" role="3cqZAp" />
        <node concept="3SKdUt" id="2OJAT4_15Ft" role="3cqZAp">
          <node concept="3SKdUq" id="2OJAT4_15Fu" role="3SKWNk">
            <property role="3SKdUp" value="Reprsent the length of the context hash as 1 byte" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15Fv" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15Fw" role="3cpWs9">
            <property role="TrG5h" value="context_hash_len_byte" />
            <node concept="10Q1$e" id="2OJAT4_15Fx" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_15Fy" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2BsdOp" id="2OJAT4_15Fz" role="33vP2m">
              <node concept="3SuevK" id="2OJAT4_15F$" role="2BsfMF">
                <node concept="3qc1$W" id="2OJAT4_15F_" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="2OqwBi" id="2OJAT4_15FA" role="3Sueug">
                  <node concept="37vLTw" id="2OJAT4_15FB" role="2Oq$k0">
                    <ref role="3cqZAo" node="2OJAT4_15G6" resolve="context_hash" />
                  </node>
                  <node concept="1Rwk04" id="2OJAT4_15FC" role="2OqNvi" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_15FD" role="3cqZAp" />
        <node concept="3SKdUt" id="1fN2f79V5Aw" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79V5Ay" role="3SKWNk">
            <property role="3SKdUp" value="The final label is the concatenation of the following:" />
          </node>
        </node>
        <node concept="3SKdUt" id="1fN2f79V6KC" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79V6KD" role="3SKWNk">
            <property role="3SKdUp" value="1. length of the required output as 2 bytes" />
          </node>
        </node>
        <node concept="3SKdUt" id="1fN2f79V6ql" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79V6qn" role="3SKWNk">
            <property role="3SKdUp" value="2. the label prepended by its length as one byte" />
          </node>
        </node>
        <node concept="3SKdUt" id="1fN2f79V7PZ" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79V7Q1" role="3SKWNk">
            <property role="3SKdUp" value="3. the context hash prepended by its length as one byte" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15FG" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15FH" role="3cpWs9">
            <property role="TrG5h" value="arrays_to_concat" />
            <node concept="10Q1$e" id="2OJAT4_15FI" role="1tU5fm">
              <node concept="10Q1$e" id="2OJAT4_15FJ" role="10Q1$1">
                <node concept="3qc1$W" id="2OJAT4_15FK" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
            <node concept="2BsdOp" id="2OJAT4_15FL" role="33vP2m">
              <node concept="37vLTw" id="2OJAT4_15FM" role="2BsfMF">
                <ref role="3cqZAo" node="2OJAT4_15ET" resolve="output_len_bytes" />
              </node>
              <node concept="37vLTw" id="2OJAT4_15FN" role="2BsfMF">
                <ref role="3cqZAo" node="2OJAT4_15F9" resolve="label_len_byte" />
              </node>
              <node concept="37vLTw" id="2OJAT4_15FO" role="2BsfMF">
                <ref role="3cqZAo" node="2OJAT4_15Fl" resolve="label_bytes" />
              </node>
              <node concept="37vLTw" id="2OJAT4_15FP" role="2BsfMF">
                <ref role="3cqZAo" node="2OJAT4_15Fw" resolve="context_hash_len_byte" />
              </node>
              <node concept="37vLTw" id="2OJAT4_15FQ" role="2BsfMF">
                <ref role="3cqZAo" node="2OJAT4_15G6" resolve="context_hash" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_15FR" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_15FS" role="3cpWs9">
            <property role="TrG5h" value="hkdf_label" />
            <node concept="10Q1$e" id="2OJAT4_15FT" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_15FU" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="2OJAT4_15FV" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <ref role="37wK5l" to="d2b1:2OJAT4$NxN3" resolve="concat" />
              <node concept="37vLTw" id="2OJAT4_15FW" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_15FH" resolve="arrays_to_concat" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79V4mY" role="3cqZAp" />
        <node concept="3cpWs6" id="2OJAT4_15FX" role="3cqZAp">
          <node concept="37vLTw" id="2OJAT4_15FY" role="3cqZAk">
            <ref role="3cqZAo" node="2OJAT4_15FS" resolve="hkdf_label" />
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_15G0" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_15G1" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_15G2" role="3clF46">
        <property role="TrG5h" value="output_len" />
        <node concept="10Oyi0" id="2OJAT4_15G3" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="2OJAT4_15G4" role="3clF46">
        <property role="TrG5h" value="label_string" />
        <node concept="17QB3L" id="2OJAT4_15G5" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="2OJAT4_15G6" role="3clF46">
        <property role="TrG5h" value="context_hash" />
        <node concept="10Q1$e" id="2OJAT4_15G7" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_15G8" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2OJAT4_15FZ" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2OJAT4zQYFA" role="jymVt" />
    <node concept="DJdLC" id="1fN2f79VdM$" role="jymVt">
      <property role="DRO8Q" value="The three functions below call HKDF Expand" />
    </node>
    <node concept="DJdLC" id="1fN2f79Vg7a" role="jymVt">
      <property role="DRO8Q" value="when the output generated is a key and a iv and a TLS secret, respectively." />
    </node>
    <node concept="DJdLC" id="1fN2f79Vf46" role="jymVt">
      <property role="DRO8Q" value="Descriptions are in RFC 8446, Section 7.3" />
    </node>
    <node concept="2tJIrI" id="1fN2f79VgNm" role="jymVt" />
    <node concept="2YIFZL" id="2OJAT4_166j" role="jymVt">
      <property role="TrG5h" value="hkdf_expand_derive_tk" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_166l" role="3clF47">
        <node concept="3SKdUt" id="1fN2f79Vlve" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79Vlvg" role="3SKWNk">
            <property role="3SKdUp" value="For AES GCM 128, the key length is 16" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_166m" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_166n" role="3cpWs9">
            <property role="TrG5h" value="hkdf_label" />
            <node concept="10Q1$e" id="2OJAT4_166o" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_166p" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="1rXfSq" id="2OJAT4_166q" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15E$" resolve="get_tls_hkdf_label" />
              <node concept="37vLTw" id="2OJAT4LJCWT" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4LJC_7" resolve="key_length" />
              </node>
              <node concept="Xl_RD" id="2OJAT4_166s" role="37wK5m">
                <property role="Xl_RC" value="key" />
              </node>
              <node concept="2ShNRf" id="2OJAT4LJDPq" role="37wK5m">
                <node concept="3$_iS1" id="2OJAT4LJDYj" role="2ShVmc">
                  <node concept="3$GHV9" id="2OJAT4LJDYl" role="3$GQph">
                    <node concept="3cmrfG" id="2OJAT4LJE1F" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="2OJAT4LJDYi" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="2OJAT4_166w" role="3cqZAp">
          <node concept="2YIFZM" id="2OJAT4_166x" role="3cqZAk">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4_00HT" resolve="get_prefix" />
            <node concept="1rXfSq" id="2OJAT4_166y" role="37wK5m">
              <ref role="37wK5l" node="2OJAT4_16PV" resolve="hkdf_expand" />
              <node concept="37vLTw" id="2OJAT4_166z" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_166D" resolve="secret" />
              </node>
              <node concept="37vLTw" id="2OJAT4_166$" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_166n" resolve="hkdf_label" />
              </node>
            </node>
            <node concept="37vLTw" id="2OJAT4LJDai" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4LJC_7" resolve="key_length" />
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_166B" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_166C" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_166D" role="3clF46">
        <property role="TrG5h" value="secret" />
        <node concept="10Q1$e" id="2OJAT4_166E" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_166F" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4LJC_7" role="3clF46">
        <property role="TrG5h" value="key_length" />
        <node concept="10Oyi0" id="2OJAT4LJCCc" role="1tU5fm" />
      </node>
      <node concept="3Tm1VV" id="2OJAT4_166A" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2OJAT4zS3we" role="jymVt" />
    <node concept="2YIFZL" id="2OJAT4_16mi" role="jymVt">
      <property role="TrG5h" value="hkdf_expand_derive_iv" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_16mk" role="3clF47">
        <node concept="3SKdUt" id="1fN2f79VlJf" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79VlJh" role="3SKWNk">
            <property role="3SKdUp" value="For AES GCM 128, the iv length is 12" />
          </node>
        </node>
        <node concept="3cpWs8" id="2OJAT4_16ml" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_16mm" role="3cpWs9">
            <property role="TrG5h" value="hkdf_label" />
            <node concept="10Q1$e" id="2OJAT4_16mn" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_16mo" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="1rXfSq" id="2OJAT4_16mp" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15E$" resolve="get_tls_hkdf_label" />
              <node concept="37vLTw" id="2OJAT4LJDnj" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4LJCCO" resolve="iv_length" />
              </node>
              <node concept="Xl_RD" id="2OJAT4_16mr" role="37wK5m">
                <property role="Xl_RC" value="iv" />
              </node>
              <node concept="2ShNRf" id="2OJAT4LJEia" role="37wK5m">
                <node concept="3$_iS1" id="2OJAT4LJEqZ" role="2ShVmc">
                  <node concept="3$GHV9" id="2OJAT4LJEr1" role="3$GQph">
                    <node concept="3cmrfG" id="2OJAT4LJEun" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="2OJAT4LJEqY" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="2OJAT4_16mx" role="3cqZAp">
          <node concept="2YIFZM" id="2OJAT4_16my" role="3cqZAk">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4_00HT" resolve="get_prefix" />
            <node concept="1rXfSq" id="2OJAT4_16mz" role="37wK5m">
              <ref role="37wK5l" node="2OJAT4_16PV" resolve="hkdf_expand" />
              <node concept="37vLTw" id="2OJAT4_16m$" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16mE" resolve="secret" />
              </node>
              <node concept="37vLTw" id="2OJAT4_16m_" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16mm" resolve="hkdf_label" />
              </node>
            </node>
            <node concept="37vLTw" id="2OJAT4LJD$4" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4LJCCO" resolve="iv_length" />
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_16mC" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_16mD" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_16mE" role="3clF46">
        <property role="TrG5h" value="secret" />
        <node concept="10Q1$e" id="2OJAT4_16mF" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_16mG" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4LJCCO" role="3clF46">
        <property role="TrG5h" value="iv_length" />
        <node concept="10Oyi0" id="2OJAT4LJCFT" role="1tU5fm" />
      </node>
      <node concept="3Tm1VV" id="2OJAT4_16mB" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2OJAT4hkvtB" role="jymVt" />
    <node concept="2YIFZL" id="2OJAT4_16Ah" role="jymVt">
      <property role="TrG5h" value="hkdf_expand_derive_secret" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2OJAT4_16Aj" role="3clF47">
        <node concept="3SKdUt" id="1fN2f79Vl3J" role="3cqZAp">
          <node concept="3SKdUq" id="1fN2f79Vl3L" role="3SKWNk">
            <property role="3SKdUp" value="The length of all TLS 1.3 secrets are 32 bytes" />
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79VlmT" role="3cqZAp" />
        <node concept="3cpWs8" id="2OJAT4_16Ao" role="3cqZAp">
          <node concept="3cpWsn" id="2OJAT4_16Ap" role="3cpWs9">
            <property role="TrG5h" value="hkdf_label" />
            <node concept="10Q1$e" id="2OJAT4_16Aq" role="1tU5fm">
              <node concept="3qc1$W" id="2OJAT4_16Ar" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="1rXfSq" id="2OJAT4_16As" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15E$" resolve="get_tls_hkdf_label" />
              <node concept="3cmrfG" id="2OJAT4_16At" role="37wK5m">
                <property role="3cmrfH" value="32" />
              </node>
              <node concept="37vLTw" id="2OJAT4_16Au" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16AF" resolve="label_string" />
              </node>
              <node concept="37vLTw" id="2OJAT4_16Av" role="37wK5m">
                <ref role="3cqZAo" node="2OJAT4_16AH" resolve="context_hash" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2OJAT4_16Aw" role="3cqZAp" />
        <node concept="3cpWs6" id="2OJAT4_16Ax" role="3cqZAp">
          <node concept="1rXfSq" id="2OJAT4_16Ay" role="3cqZAk">
            <ref role="37wK5l" node="2OJAT4_16PV" resolve="hkdf_expand" />
            <node concept="37vLTw" id="2OJAT4_16Az" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_16AC" resolve="secret" />
            </node>
            <node concept="37vLTw" id="2OJAT4_16A$" role="37wK5m">
              <ref role="3cqZAo" node="2OJAT4_16Ap" resolve="hkdf_label" />
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="2OJAT4_16AA" role="3clF45">
        <node concept="3qc1$W" id="2OJAT4_16AB" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_16AC" role="3clF46">
        <property role="TrG5h" value="secret" />
        <node concept="10Q1$e" id="2OJAT4_16AD" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_16AE" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2OJAT4_16AF" role="3clF46">
        <property role="TrG5h" value="label_string" />
        <node concept="17QB3L" id="2OJAT4_16AG" role="1tU5fm" />
      </node>
      <node concept="37vLTG" id="2OJAT4_16AH" role="3clF46">
        <property role="TrG5h" value="context_hash" />
        <node concept="10Q1$e" id="2OJAT4_16AI" role="1tU5fm">
          <node concept="3qc1$W" id="2OJAT4_16AJ" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="2OJAT4_16A_" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="1fN2f79Viw5" role="jymVt" />
    <node concept="2tJIrI" id="2OJAT4hk$_7" role="jymVt" />
    <node concept="2tJIrI" id="2OJAT4$Z6et" role="jymVt" />
    <node concept="3Tm1VV" id="2OJAT4$Z6ee" role="1B3o_S" />
  </node>
  <node concept="312cEu" id="64TdDMCKqoB">
    <property role="TrG5h" value="TLSKeySchedule" />
    <node concept="2tJIrI" id="30zMb0dGEhK" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dGECb" role="jymVt">
      <property role="DRO8Q" value="NOTATION is from https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="3Tm1VV" id="64TdDMCKqoC" role="1B3o_S" />
    <node concept="2tJIrI" id="64TdDMCKqrt" role="jymVt" />
    <node concept="DJdLC" id="30zMb0d$ChH" role="jymVt">
      <property role="DRO8Q" value="This class contains functions that compute the different types of TLS1.3 Key Schedule" />
    </node>
    <node concept="DJdLC" id="30zMb0d$Dke" role="jymVt">
      <property role="DRO8Q" value="Input: " />
    </node>
    <node concept="DJdLC" id="30zMb0d$G5Q" role="jymVt">
      <property role="DRO8Q" value="  - handshake transcript" />
    </node>
    <node concept="DJdLC" id="30zMb0d$H4i" role="jymVt">
      <property role="DRO8Q" value="  - client's secrets (PSK and/or DHE share)" />
    </node>
    <node concept="DJdLC" id="30zMb0d$FkF" role="jymVt">
      <property role="DRO8Q" value="  - application data ciphertext" />
    </node>
    <node concept="DJdLC" id="30zMb0d$EnP" role="jymVt">
      <property role="DRO8Q" value="Output:" />
    </node>
    <node concept="DJdLC" id="30zMb0d$HYh" role="jymVt">
      <property role="DRO8Q" value="  - client's application traffic key" />
    </node>
    <node concept="DJdLC" id="30zMb0d$IEh" role="jymVt">
      <property role="DRO8Q" value="  - decryption of the applicaton data" />
    </node>
    <node concept="DJdLC" id="30zMb0d$KKe" role="jymVt">
      <property role="DRO8Q" value="." />
    </node>
    <node concept="DJdLC" id="30zMb0d$Meo" role="jymVt">
      <property role="DRO8Q" value="This is done for 4 types of TLS 1.3 Key Schedule methods:" />
    </node>
    <node concept="DJdLC" id="30zMb0d$N0o" role="jymVt">
      <property role="DRO8Q" value="  - 0RTT" />
    </node>
    <node concept="DJdLC" id="30zMb0d$NMv" role="jymVt">
      <property role="DRO8Q" value="  - Baseline 1RTT" />
    </node>
    <node concept="DJdLC" id="30zMb0d$Oy1" role="jymVt">
      <property role="DRO8Q" value="  - Shortcut 1RTT" />
    </node>
    <node concept="DJdLC" id="30zMb0d$Ph_" role="jymVt">
      <property role="DRO8Q" value="  - Amortized Opening" />
    </node>
    <node concept="2tJIrI" id="30zMb0d$Slo" role="jymVt" />
    <node concept="DJdLC" id="30zMb0d$T1D" role="jymVt">
      <property role="DRO8Q" value="The notation for all variables in this class is from:" />
    </node>
    <node concept="DJdLC" id="30zMb0d$TTk" role="jymVt">
      <property role="DRO8Q" value="https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="2tJIrI" id="30zMb0d$XRp" role="jymVt" />
    <node concept="DJdLC" id="30zMb0d$YzK" role="jymVt">
      <property role="DRO8Q" value="The key dervation process for the different methods is in Figure 2" />
    </node>
    <node concept="2tJIrI" id="30zMb0d$BTd" role="jymVt" />
    <node concept="2tJIrI" id="30zMb0dAad7" role="jymVt" />
    <node concept="2tJIrI" id="30zMb0dAaz_" role="jymVt" />
    <node concept="2tJIrI" id="30zMb0dAaU4" role="jymVt" />
    <node concept="DJdLC" id="30zMb0d$QNt" role="jymVt">
      <property role="DRO8Q" value="0RTT method is a &quot;session resumption&quot; feature offered by TLS" />
    </node>
    <node concept="DJdLC" id="30zMb0d$ZED" role="jymVt">
      <property role="DRO8Q" value="where the client and server share a PSK (established in a previous session)" />
    </node>
    <node concept="DJdLC" id="30zMb0d_1v0" role="jymVt">
      <property role="DRO8Q" value="and the PSK can be used to send &quot;early data&quot; in the client's first message " />
    </node>
    <node concept="DJdLC" id="30zMb0d_2jU" role="jymVt">
      <property role="DRO8Q" value="without a full handshake" />
    </node>
    <node concept="DJdLC" id="30zMb0d_36p" role="jymVt">
      <property role="DRO8Q" value="See Figure 2 from https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="2tJIrI" id="30zMb0dA9O3" role="jymVt" />
    <node concept="DJdLC" id="30zMb0d_jaI" role="jymVt">
      <property role="DRO8Q" value="The function broadly does the following steps:" />
    </node>
    <node concept="DJdLC" id="30zMb0d_jVS" role="jymVt">
      <property role="DRO8Q" value="(1) Using the PSK and transcript hashes, compute the binder" />
    </node>
    <node concept="DJdLC" id="30zMb0dAe8Q" role="jymVt">
      <property role="DRO8Q" value="(2) Verify that it is equal to the REAL_BINDER from the transcript" />
    </node>
    <node concept="DJdLC" id="30zMb0dAf0v" role="jymVt">
      <property role="DRO8Q" value="(3) Now, compute the traffic keys and decrypt the ciphertext" />
    </node>
    <node concept="2YIFZL" id="64TdDMDBykh" role="jymVt">
      <property role="TrG5h" value="get0RTT" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="64TdDMDBykj" role="3clF47">
        <node concept="3clFbH" id="64TdDMDBykk" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDBykl" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDBykm" role="3cpWs9">
            <property role="TrG5h" value="ES" />
            <node concept="10Q1$e" id="64TdDMDBykn" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDByko" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDBykp" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="2YIFZM" id="64TdDMDBykq" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="64TdDMDBykr" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
              <node concept="37vLTw" id="64TdDMDByks" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBylP" resolve="PSK" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4c$AeS51HkG" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDBykt" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDByku" role="3cpWs9">
            <property role="TrG5h" value="dES" />
            <node concept="10Q1$e" id="64TdDMDBykv" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDBykw" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDBykx" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="64TdDMDByky" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBykm" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="64TdDMDBykz" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="64TdDMDByk$" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDByk_" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDBykC" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDBykD" role="3cpWs9">
            <property role="TrG5h" value="BK" />
            <node concept="10Q1$e" id="64TdDMDBykE" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDBykF" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDBykG" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="64TdDMDBykH" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBykm" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="64TdDMDBykI" role="37wK5m">
                <property role="Xl_RC" value="res binder" />
              </node>
              <node concept="2YIFZM" id="64TdDMDBykJ" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDBykK" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDBykL" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDBykM" role="3cpWs9">
            <property role="TrG5h" value="fk_B" />
            <node concept="10Q1$e" id="64TdDMDBykN" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDBykO" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDBykP" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="64TdDMDBykQ" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBykD" resolve="BK" />
              </node>
              <node concept="Xl_RD" id="64TdDMDBykR" role="37wK5m">
                <property role="Xl_RC" value="finished" />
              </node>
              <node concept="2ShNRf" id="64TdDMDBykS" role="37wK5m">
                <node concept="3$_iS1" id="64TdDMDBykT" role="2ShVmc">
                  <node concept="3$GHV9" id="64TdDMDBykU" role="3$GQph">
                    <node concept="3cmrfG" id="64TdDMDBykV" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="64TdDMDBykW" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDBykX" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0d_9kO" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0d_9kQ" role="3SKWNk">
            <property role="3SKdUp" value="This is the binder derived by the purported PSK that was given as a witness to the circuit" />
          </node>
        </node>
        <node concept="3cpWs8" id="64TdDMDBykY" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDBykZ" role="3cpWs9">
            <property role="TrG5h" value="derived_binder" />
            <node concept="10Q1$e" id="64TdDMDByl0" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDByl1" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDByl2" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="64TdDMDByl3" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBykM" resolve="fk_B" />
              </node>
              <node concept="37vLTw" id="64TdDMDByl4" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBylV" resolve="H_5" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="30zMb0d_9I2" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0d_gid" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0d_gif" role="3SKWNk">
            <property role="3SKdUp" value="Verify that the derived binder is the same as the one from the transcript" />
          </node>
        </node>
        <node concept="3s6pcg" id="30zMb0d_d_1" role="3cqZAp">
          <node concept="2YIFZM" id="30zMb0d_e8V" role="3s6pch">
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <node concept="37vLTw" id="30zMb0d_eGU" role="37wK5m">
              <ref role="3cqZAo" node="64TdDMDBylY" resolve="REAL_BINDER" />
            </node>
          </node>
          <node concept="2YIFZM" id="30zMb0d_feU" role="3s6pci">
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <node concept="37vLTw" id="30zMb0d_fuT" role="37wK5m">
              <ref role="3cqZAo" node="64TdDMDBykZ" resolve="derived_binder" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDByl6" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDByl7" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDByl8" role="3cpWs9">
            <property role="TrG5h" value="ETS" />
            <node concept="10Q1$e" id="64TdDMDByl9" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDByla" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDBylb" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="64TdDMDBylc" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBykm" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="64TdDMDByld" role="37wK5m">
                <property role="Xl_RC" value="c e traffic" />
              </node>
              <node concept="37vLTw" id="64TdDMDByle" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBylS" resolve="H_1" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDBylf" role="3cqZAp" />
        <node concept="3cpWs8" id="64TdDMDBylh" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDByli" role="3cpWs9">
            <property role="TrG5h" value="tk_early" />
            <node concept="10Q1$e" id="64TdDMDBylj" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDBylk" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDByll" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <node concept="37vLTw" id="64TdDMDBylm" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDByl8" resolve="ETS" />
              </node>
              <node concept="3cmrfG" id="64TdDMDByln" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="64TdDMDBylo" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDBylp" role="3cpWs9">
            <property role="TrG5h" value="iv_early" />
            <node concept="10Q1$e" id="64TdDMDBylq" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDBylr" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDByls" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="64TdDMDBylt" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDByl8" resolve="ETS" />
              </node>
              <node concept="3cmrfG" id="64TdDMDBylu" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="64TdDMDBylv" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dAcLz" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dAcL_" role="3SKWNk">
            <property role="3SKdUp" value="decrypt the plaintext" />
          </node>
        </node>
        <node concept="3cpWs8" id="64TdDMDBylx" role="3cqZAp">
          <node concept="3cpWsn" id="64TdDMDByly" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="64TdDMDBylz" role="1tU5fm">
              <node concept="3qc1$W" id="64TdDMDByl$" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="64TdDMDByl_" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="64TdDMDBylA" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDByli" resolve="tk_early" />
              </node>
              <node concept="37vLTw" id="64TdDMDBylB" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBylp" resolve="iv_early" />
              </node>
              <node concept="37vLTw" id="64TdDMDBylC" role="37wK5m">
                <ref role="3cqZAo" node="64TdDMDBym1" resolve="dns_ciphertext" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="64TdDN5wB76" role="3cqZAp">
          <node concept="37vLTw" id="64TdDNdwwEk" role="3cqZAk">
            <ref role="3cqZAo" node="64TdDMDByly" resolve="dns_plaintext" />
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="64TdDMDBylM" role="3clF45">
        <node concept="3qc1$W" id="64TdDMDBylO" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="64TdDMDBylP" role="3clF46">
        <property role="TrG5h" value="PSK" />
        <node concept="10Q1$e" id="64TdDMDBylQ" role="1tU5fm">
          <node concept="3qc1$W" id="64TdDMDBylR" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="64TdDMDBylS" role="3clF46">
        <property role="TrG5h" value="H_1" />
        <node concept="10Q1$e" id="64TdDMDBylT" role="1tU5fm">
          <node concept="3qc1$W" id="64TdDMDBylU" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="64TdDMDBylV" role="3clF46">
        <property role="TrG5h" value="H_5" />
        <node concept="10Q1$e" id="64TdDMDBylW" role="1tU5fm">
          <node concept="3qc1$W" id="64TdDMDBylX" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="64TdDMDBylY" role="3clF46">
        <property role="TrG5h" value="REAL_BINDER" />
        <node concept="10Q1$e" id="64TdDMDBylZ" role="1tU5fm">
          <node concept="3qc1$W" id="64TdDMDBym0" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="64TdDMDBym1" role="3clF46">
        <property role="TrG5h" value="dns_ciphertext" />
        <node concept="10Q1$e" id="64TdDMDBym2" role="1tU5fm">
          <node concept="3qc1$W" id="64TdDMDBym3" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="64TdDMDBylL" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="6IcGJgA1y3F" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dAdoj" role="jymVt">
      <property role="DRO8Q" value="This is the baseline 1RTT handshake key derivation" />
    </node>
    <node concept="DJdLC" id="30zMb0dAg5u" role="jymVt">
      <property role="DRO8Q" value="Steps:" />
    </node>
    <node concept="DJdLC" id="30zMb0dAii0" role="jymVt">
      <property role="DRO8Q" value="(1) Verify and derive the EC DHE secret" />
    </node>
    <node concept="DJdLC" id="30zMb0dAj9m" role="jymVt">
      <property role="DRO8Q" value="(2) Compute server handshake keys" />
    </node>
    <node concept="DJdLC" id="30zMb0dAknw" role="jymVt">
      <property role="DRO8Q" value="(3) Decrypt the encrypted parts of CT3 (CH || SH || ServExt) to get TR3" />
    </node>
    <node concept="DJdLC" id="30zMb0dAllE" role="jymVt">
      <property role="DRO8Q" value="(3) Hash TR3" />
    </node>
    <node concept="DJdLC" id="30zMb0dAm82" role="jymVt">
      <property role="DRO8Q" value="(5) Derive client traffic keys and decrypt ciphertext" />
    </node>
    <node concept="2tJIrI" id="30zMb0dH5Hf" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dH658" role="jymVt">
      <property role="DRO8Q" value="Inputs: DHE share and public points A and B" />
    </node>
    <node concept="DJdLC" id="30zMb0dH6ye" role="jymVt">
      <property role="DRO8Q" value="transcript hash H2 = Hash(CH || SH)" />
    </node>
    <node concept="DJdLC" id="30zMb0dH6WS" role="jymVt">
      <property role="DRO8Q" value="CH_SH - Transcript ClientHello || ServerHello and its length" />
    </node>
    <node concept="DJdLC" id="30zMb0dH7r8" role="jymVt">
      <property role="DRO8Q" value="ServExt_ct - the encrypted Server Extensions and its length" />
    </node>
    <node concept="DJdLC" id="30zMb0dH7PM" role="jymVt">
      <property role="DRO8Q" value="ServExt_tail_ct is the part of ServExt_ct that doesn't fit into a whole SHA block" />
    </node>
    <node concept="DJdLC" id="30zMb0dHaYS" role="jymVt">
      <property role="DRO8Q" value="appl_ct - the application data ciphertext" />
    </node>
    <node concept="2YIFZL" id="6IcGJgA1ynG" role="jymVt">
      <property role="TrG5h" value="get1RTT" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="6IcGJgA1ynH" role="3clF47">
        <node concept="3clFbH" id="30zMb0dAn9p" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1yo1" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yo2" role="3cpWs9">
            <property role="TrG5h" value="ES" />
            <node concept="10Q1$e" id="6IcGJgA1yo3" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yo4" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yo5" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <node concept="2YIFZM" id="6IcGJgA1yo6" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="6IcGJgA1yo7" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
              <node concept="2YIFZM" id="6IcGJgA1yo8" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <node concept="3cmrfG" id="6IcGJgA1yo9" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA1yob" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yoc" role="3cpWs9">
            <property role="TrG5h" value="dES" />
            <node concept="10Q1$e" id="6IcGJgA1yod" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yoe" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yof" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1yog" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yo2" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="6IcGJgA1yoh" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="6IcGJgA1yoi" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79VpMk" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dAo0Y" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dAo10" role="3SKWNk">
            <property role="3SKdUp" value="This function's goals:" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dAoWh" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dAoWj" role="3SKWNk">
            <property role="3SKdUp" value="(1) Verify that G^sk = A where G is the generator of secp256" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dApQW" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dApQY" role="3SKWNk">
            <property role="3SKdUp" value="(2) Compute B^sk to obtain the DHE secret" />
          </node>
        </node>
        <node concept="3cpWs8" id="1fN2f79SIBp" role="3cqZAp">
          <node concept="3cpWsn" id="1fN2f79SIBs" role="3cpWs9">
            <property role="TrG5h" value="DHE" />
            <node concept="10Q1$e" id="1fN2f79SIZ$" role="1tU5fm">
              <node concept="3qc1$W" id="1fN2f79SIBn" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="1fN2f79SKkA" role="33vP2m">
              <ref role="1Pybhc" to="rktg:2OJAT4DN4UY" resolve="ECDHE" />
              <ref role="37wK5l" to="rktg:1fN2f79SE7n" resolve="DHExchange" />
              <node concept="37vLTw" id="1fN2f79SKvD" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqd" resolve="Ax" />
              </node>
              <node concept="37vLTw" id="1fN2f79SKxG" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqf" resolve="Ay" />
              </node>
              <node concept="37vLTw" id="1fN2f79SK$O" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqh" resolve="Bx" />
              </node>
              <node concept="37vLTw" id="1fN2f79SKB5" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqj" resolve="By" />
              </node>
              <node concept="37vLTw" id="1fN2f79SLye" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqb" resolve="DHE_share" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79SMMB" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1yot" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1you" role="3cpWs9">
            <property role="TrG5h" value="HS" />
            <node concept="10Q1$e" id="6IcGJgA1yov" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yow" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yox" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1yoy" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoc" resolve="dES" />
              </node>
              <node concept="37vLTw" id="1fN2f79SLEJ" role="37wK5m">
                <ref role="3cqZAo" node="1fN2f79SIBs" resolve="DHE" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1fN2f79SOrb" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1yo_" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yoA" role="3cpWs9">
            <property role="TrG5h" value="SHTS" />
            <node concept="10Q1$e" id="6IcGJgA1yoB" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yoC" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yoD" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1yoE" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1you" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="6IcGJgA1yoF" role="37wK5m">
                <property role="Xl_RC" value="s hs traffic" />
              </node>
              <node concept="37vLTw" id="6IcGJgA1yoG" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yql" resolve="H2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1yoH" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dArf_" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dI8Mb" role="3SKWNk">
            <property role="3SKdUp" value="traffic key and iv for &quot;server handshake&quot; messages" />
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA1yoI" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yoJ" role="3cpWs9">
            <property role="TrG5h" value="tk_shs" />
            <node concept="10Q1$e" id="6IcGJgA1yoK" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yoL" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yoM" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1yoN" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoA" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="6IcGJgA1yoO" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA1yoP" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yoQ" role="3cpWs9">
            <property role="TrG5h" value="iv_shs" />
            <node concept="10Q1$e" id="6IcGJgA1yoR" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yoS" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yoT" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="6IcGJgA1yoU" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoA" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="6IcGJgA1yoV" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1yoW" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1yoX" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yoY" role="3cpWs9">
            <property role="TrG5h" value="dHS" />
            <node concept="10Q1$e" id="6IcGJgA1yoZ" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yp0" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yp1" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1yp2" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1you" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="6IcGJgA1yp3" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="6IcGJgA1yp4" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1yp5" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1yp6" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1yp7" role="3cpWs9">
            <property role="TrG5h" value="MS" />
            <node concept="10Q1$e" id="6IcGJgA1yp8" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yp9" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1ypa" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1ypb" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoY" resolve="dHS" />
              </node>
              <node concept="2YIFZM" id="6IcGJgA1ypc" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <node concept="3cmrfG" id="6IcGJgA1ypd" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1ype" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dGLEP" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dGLER" role="3SKWNk">
            <property role="3SKdUp" value="Decrypt the server extensions with the server's handshake traffic keys" />
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA1ypf" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1ypg" role="3cpWs9">
            <property role="TrG5h" value="ServExt" />
            <node concept="10Q1$e" id="6IcGJgA1yph" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1ypi" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1ypj" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="6IcGJgA1ypk" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoJ" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="6IcGJgA1ypl" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoQ" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="6IcGJgA1ypm" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqr" resolve="ServExt_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dGMyC" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dGMyE" role="3SKWNk">
            <property role="3SKdUp" value="Now, we need to decrypt the ServExt_tail." />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dGN6P" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dGN6R" role="3SKWNk">
            <property role="3SKdUp" value="As we are using AES GCM, we need to find the exact block number that the tail starts at." />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dGQBo" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dGQBq" role="3SKWNk">
            <property role="3SKdUp" value="One AES block = 16 bytes" />
          </node>
        </node>
        <node concept="3cpWs8" id="2ieUQWSqa0K" role="3cqZAp">
          <node concept="3cpWsn" id="2ieUQWSqa0L" role="3cpWs9">
            <property role="TrG5h" value="gcm_block_number" />
            <node concept="3qc1$W" id="2ieUQWSqa0M" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="17qRlL" id="2ieUQWSqa0N" role="33vP2m">
              <node concept="3SuevK" id="2ieUQWSqa0O" role="3uHU7w">
                <node concept="3qc1$W" id="2ieUQWSqa0P" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="2ieUQWSqa0Q" role="3Sueug">
                  <property role="3cmrfH" value="4" />
                </node>
              </node>
              <node concept="3SuevK" id="2ieUQWSqa0R" role="3uHU7B">
                <node concept="3qc1$W" id="2ieUQWSqa0S" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="FJ1c_" id="2ieUQWSqa0T" role="3Sueug">
                  <node concept="3SuevK" id="2ieUQWSqa0U" role="3uHU7w">
                    <node concept="3qc1$W" id="2ieUQWSqa0V" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="3cmrfG" id="2ieUQWSqa0W" role="3Sueug">
                      <property role="3cmrfH" value="64" />
                    </node>
                  </node>
                  <node concept="37vLTw" id="2ieUQWSqa12" role="3uHU7B">
                    <ref role="3cqZAo" node="6IcGJgA1$x2" resolve="ServExt_len" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2ieUQWSq9Aj" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dGWRg" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dGWRi" role="3SKWNk">
            <property role="3SKdUp" value="Returns the decryption starting at the GCM counter " />
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgIWomC" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgIWomF" role="3cpWs9">
            <property role="TrG5h" value="Serv_Ext_tail" />
            <node concept="10Q1$e" id="6IcGJgIWoJN" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgIWomA" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgIWq1i" role="33vP2m">
              <ref role="37wK5l" to="liel:6IcGJgJ0RWl" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="6IcGJgIWq3l" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoJ" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="6IcGJgIWq5E" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yoQ" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="6IcGJgIWqr3" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1$TN" resolve="ServExt_tail_ct" />
              </node>
              <node concept="37vLTw" id="2ieUQWSqfRs" role="37wK5m">
                <ref role="3cqZAo" node="2ieUQWSqa0L" resolve="gcm_block_number" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1ypn" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dH0RC" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dH0RE" role="3SKWNk">
            <property role="3SKdUp" value="This transcript is CH || SH || ServExt" />
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA24Ku" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA24Kx" role="3cpWs9">
            <property role="TrG5h" value="TR3" />
            <node concept="10Q1$e" id="6IcGJgA257t" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA24Ks" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA25BM" role="33vP2m">
              <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <node concept="37vLTw" id="6IcGJgA25DR" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqo" resolve="CH_SH" />
              </node>
              <node concept="37vLTw" id="6IcGJgA25Mg" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1ypg" resolve="ServExt" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA24m1" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dH1ON" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dH1OP" role="3SKWNk">
            <property role="3SKdUp" value="As we don't know the true length of ServExt, the variable's size is a fixed upper bound" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dH2yA" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dH2yC" role="3SKWNk">
            <property role="3SKdUp" value="However, we only require a hash of the true transcript, which is a prefix of the variable" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dH5a_" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dH5aB" role="3SKWNk">
            <property role="3SKdUp" value="of length CH_SH_len + ServExt_len" />
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA28t8" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA28tb" role="3cpWs9">
            <property role="TrG5h" value="H3" />
            <node concept="10Q1$e" id="6IcGJgA28Pl" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA28t6" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA29gO" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              <ref role="37wK5l" to="d2b1:6IcGJgybQ6o" resolve="sha2_of_prefix" />
              <node concept="37vLTw" id="6IcGJgA29kv" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA24Kx" resolve="TR3" />
              </node>
              <node concept="3cpWs3" id="7hpWUTxJia7" role="37wK5m">
                <node concept="37vLTw" id="7hpWUTxJiyp" role="3uHU7B">
                  <ref role="3cqZAo" node="6IcGJgA1$5X" resolve="CH_SH_len" />
                </node>
                <node concept="37vLTw" id="6IcGJgA2aUt" role="3uHU7w">
                  <ref role="3cqZAo" node="6IcGJgA1$x2" resolve="ServExt_len" />
                </node>
              </node>
              <node concept="37vLTw" id="6IcGJgIWrxU" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgIWomF" resolve="Serv_Ext_tail" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA283Y" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1ypx" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1ypy" role="3cpWs9">
            <property role="TrG5h" value="CATS" />
            <node concept="10Q1$e" id="6IcGJgA1ypz" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1yp$" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1yp_" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="6IcGJgA1ypA" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yp7" resolve="MS" />
              </node>
              <node concept="Xl_RD" id="6IcGJgA1ypB" role="37wK5m">
                <property role="Xl_RC" value="c ap traffic" />
              </node>
              <node concept="37vLTw" id="6IcGJgA2hgY" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA28tb" resolve="H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1ypD" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1ypE" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1ypF" role="3cpWs9">
            <property role="TrG5h" value="tk_capp" />
            <node concept="10Q1$e" id="6IcGJgA1ypG" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1ypH" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1ypI" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <node concept="37vLTw" id="6IcGJgA1ypJ" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1ypy" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="6IcGJgA1ypK" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgA1ypL" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1ypM" role="3cpWs9">
            <property role="TrG5h" value="iv_capp" />
            <node concept="10Q1$e" id="6IcGJgA1ypN" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1ypO" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1ypP" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="6IcGJgA1ypQ" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1ypy" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="6IcGJgA1ypR" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1ypS" role="3cqZAp" />
        <node concept="3cpWs8" id="6IcGJgA1ypT" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgA1ypU" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="6IcGJgA1ypV" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgA1ypW" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="6IcGJgA1ypX" role="33vP2m">
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="6IcGJgA1ypY" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1ypF" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="6IcGJgA1ypZ" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1ypM" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="6IcGJgA1yq0" role="37wK5m">
                <ref role="3cqZAo" node="6IcGJgA1yqu" resolve="appl_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="6IcGJgA1yq1" role="3cqZAp" />
        <node concept="3cpWs6" id="6IcGJgA1yq2" role="3cqZAp">
          <node concept="2ShNRf" id="6IcGJgA1yq3" role="3cqZAk">
            <node concept="3g6Rrh" id="6IcGJgA1yq4" role="2ShVmc">
              <node concept="10Q1$e" id="6IcGJgA1yq5" role="3g7fb8">
                <node concept="3qc1$W" id="6IcGJgA1yq6" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="37vLTw" id="6IcGJgA1yq7" role="3g7hyw">
                <ref role="3cqZAo" node="6IcGJgA1ypU" resolve="dns_plaintext" />
              </node>
              <node concept="37vLTw" id="6IcGJgDr9c3" role="3g7hyw">
                <ref role="3cqZAo" node="6IcGJgA1ypF" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="6IcGJgDr9yJ" role="3g7hyw">
                <ref role="3cqZAo" node="6IcGJgA1ypM" resolve="iv_capp" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="6IcGJgA1yq8" role="3clF45">
        <node concept="10Q1$e" id="6IcGJgA1yq9" role="10Q1$1">
          <node concept="3qc1$W" id="6IcGJgA1yqa" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqb" role="3clF46">
        <property role="TrG5h" value="DHE_share" />
        <node concept="3qc1$W" id="6IcGJgA1yqc" role="1tU5fm">
          <property role="3qc1Xj" value="256" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqd" role="3clF46">
        <property role="TrG5h" value="Ax" />
        <node concept="2D7PWU" id="6IcGJgA1yqe" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqf" role="3clF46">
        <property role="TrG5h" value="Ay" />
        <node concept="2D7PWU" id="6IcGJgA1yqg" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqh" role="3clF46">
        <property role="TrG5h" value="Bx" />
        <node concept="2D7PWU" id="6IcGJgA1yqi" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqj" role="3clF46">
        <property role="TrG5h" value="By" />
        <node concept="2D7PWU" id="6IcGJgA1yqk" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yql" role="3clF46">
        <property role="TrG5h" value="H2" />
        <node concept="10Q1$e" id="6IcGJgA1yqm" role="1tU5fm">
          <node concept="3qc1$W" id="6IcGJgA1yqn" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqo" role="3clF46">
        <property role="TrG5h" value="CH_SH" />
        <node concept="10Q1$e" id="6IcGJgA1yqp" role="1tU5fm">
          <node concept="3qc1$W" id="6IcGJgA1yqq" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1$5X" role="3clF46">
        <property role="TrG5h" value="CH_SH_len" />
        <node concept="3qc1$W" id="6IcGJgA1$rP" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqr" role="3clF46">
        <property role="TrG5h" value="ServExt_ct" />
        <node concept="10Q1$e" id="6IcGJgA1yqs" role="1tU5fm">
          <node concept="3qc1$W" id="6IcGJgA1yqt" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1$x2" role="3clF46">
        <property role="TrG5h" value="ServExt_len" />
        <node concept="3qc1$W" id="6IcGJgA1$QW" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1$TN" role="3clF46">
        <property role="TrG5h" value="ServExt_tail_ct" />
        <node concept="10Q1$e" id="6IcGJgA1_h2" role="1tU5fm">
          <node concept="3qc1$W" id="6IcGJgA1_fJ" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="6IcGJgA1yqu" role="3clF46">
        <property role="TrG5h" value="appl_ct" />
        <node concept="10Q1$e" id="6IcGJgA1yqv" role="1tU5fm">
          <node concept="3qc1$W" id="6IcGJgA1yqw" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="6IcGJgA1yqx" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="6IcGJgA1ydF" role="jymVt" />
    <node concept="2tJIrI" id="30zMb0dAsKj" role="jymVt" />
    <node concept="1X3_iC" id="30zMb0dBkEm" role="lGtFl">
      <property role="3V$3am" value="member" />
      <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1107461130800/5375687026011219971" />
      <node concept="2YIFZL" id="7hpWUTz9SYB" role="8Wnug">
        <property role="TrG5h" value="get1RTT_resumption" />
        <property role="DiZV1" value="false" />
        <property role="od$2w" value="false" />
        <property role="2aFKle" value="false" />
        <node concept="3clFbS" id="7hpWUTz9SYC" role="3clF47">
          <node concept="3clFbH" id="7hpWUTz9SYD" role="3cqZAp" />
          <node concept="3clFbH" id="7hpWUTz9SYV" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SYW" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SYX" role="3cpWs9">
              <property role="TrG5h" value="ES" />
              <node concept="10Q1$e" id="7hpWUTz9SYY" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SYZ" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZ0" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="2YIFZM" id="7hpWUTz9SZ1" role="37wK5m">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                  <node concept="3cmrfG" id="7hpWUTz9SZ2" role="37wK5m">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
                <node concept="37vLTw" id="7hpWUTz9Wbj" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9URm" resolve="PSK" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9SZ5" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SZ6" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZ7" role="3cpWs9">
              <property role="TrG5h" value="dES" />
              <node concept="10Q1$e" id="7hpWUTz9SZ8" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZ9" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZa" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZb" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SYX" resolve="ES" />
                </node>
                <node concept="Xl_RD" id="7hpWUTz9SZc" role="37wK5m">
                  <property role="Xl_RC" value="derived" />
                </node>
                <node concept="2YIFZM" id="7hpWUTz9SZd" role="37wK5m">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                  <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="1fN2f79VqLJ" role="3cqZAp" />
          <node concept="3cpWs8" id="1fN2f79Vrzo" role="3cqZAp">
            <node concept="3cpWsn" id="1fN2f79Vrzp" role="3cpWs9">
              <property role="TrG5h" value="DHE" />
              <node concept="10Q1$e" id="1fN2f79Vrzq" role="1tU5fm">
                <node concept="3qc1$W" id="1fN2f79Vrzr" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="1fN2f79Vrzs" role="33vP2m">
                <ref role="37wK5l" to="rktg:1fN2f79SE7n" resolve="DHExchange" />
                <ref role="1Pybhc" to="rktg:2OJAT4DN4UY" resolve="ECDHE" />
                <node concept="37vLTw" id="1fN2f79Vrzt" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1t" resolve="A_x" />
                </node>
                <node concept="37vLTw" id="1fN2f79Vrzu" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1v" resolve="A_y" />
                </node>
                <node concept="37vLTw" id="1fN2f79Vrzv" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1x" resolve="B_x" />
                </node>
                <node concept="37vLTw" id="1fN2f79Vrzw" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1z" resolve="B_y" />
                </node>
                <node concept="37vLTw" id="1fN2f79VuRX" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1r" resolve="ecdhe_sk" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="1fN2f79VraK" role="3cqZAp" />
          <node concept="3clFbH" id="7hpWUTz9SZn" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SZo" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZp" role="3cpWs9">
              <property role="TrG5h" value="HS" />
              <node concept="10Q1$e" id="7hpWUTz9SZq" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZr" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZs" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZt" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZ7" resolve="dES" />
                </node>
                <node concept="37vLTw" id="1fN2f79VrZ1" role="37wK5m">
                  <ref role="3cqZAo" node="1fN2f79Vrzp" resolve="DHE" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9SZv" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SZw" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZx" role="3cpWs9">
              <property role="TrG5h" value="SHTS" />
              <node concept="10Q1$e" id="7hpWUTz9SZy" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZz" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZ$" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZ_" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZp" resolve="HS" />
                </node>
                <node concept="Xl_RD" id="7hpWUTz9SZA" role="37wK5m">
                  <property role="Xl_RC" value="s hs traffic" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9SZB" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1_" resolve="H_2" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9SZC" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SZD" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZE" role="3cpWs9">
              <property role="TrG5h" value="tk_shs" />
              <node concept="10Q1$e" id="7hpWUTz9SZF" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZG" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZH" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZI" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZx" resolve="SHTS" />
                </node>
                <node concept="3cmrfG" id="7hpWUTz9SZJ" role="37wK5m">
                  <property role="3cmrfH" value="16" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWs8" id="7hpWUTz9SZK" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZL" role="3cpWs9">
              <property role="TrG5h" value="iv_shs" />
              <node concept="10Q1$e" id="7hpWUTz9SZM" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZN" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZO" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZP" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZx" resolve="SHTS" />
                </node>
                <node concept="3cmrfG" id="7hpWUTz9SZQ" role="37wK5m">
                  <property role="3cmrfH" value="12" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9SZR" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9SZS" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9SZT" role="3cpWs9">
              <property role="TrG5h" value="dHS" />
              <node concept="10Q1$e" id="7hpWUTz9SZU" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9SZV" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9SZW" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9SZX" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZp" resolve="HS" />
                </node>
                <node concept="Xl_RD" id="7hpWUTz9SZY" role="37wK5m">
                  <property role="Xl_RC" value="derived" />
                </node>
                <node concept="2YIFZM" id="7hpWUTz9SZZ" role="37wK5m">
                  <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                  <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T00" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T01" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T02" role="3cpWs9">
              <property role="TrG5h" value="MS" />
              <node concept="10Q1$e" id="7hpWUTz9T03" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T04" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T05" role="33vP2m">
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <node concept="37vLTw" id="7hpWUTz9T06" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZT" resolve="dHS" />
                </node>
                <node concept="2YIFZM" id="7hpWUTz9T07" role="37wK5m">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                  <node concept="3cmrfG" id="7hpWUTz9T08" role="37wK5m">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T09" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0a" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0b" role="3cpWs9">
              <property role="TrG5h" value="ct3_dec" />
              <node concept="10Q1$e" id="7hpWUTz9T0c" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0d" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0e" role="33vP2m">
                <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <node concept="37vLTw" id="7hpWUTz9T0f" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZE" resolve="tk_shs" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0g" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZL" resolve="iv_shs" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0h" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1H" resolve="ct_3" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="2ieUQWSqhgh" role="3cqZAp" />
          <node concept="3cpWs8" id="2ieUQWSqhlM" role="3cqZAp">
            <node concept="3cpWsn" id="2ieUQWSqhlN" role="3cpWs9">
              <property role="TrG5h" value="num_aes_blocks" />
              <node concept="3qc1$W" id="2ieUQWSqhlO" role="1tU5fm">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="17qRlL" id="2ieUQWSqhlP" role="33vP2m">
                <node concept="3SuevK" id="2ieUQWSqhlQ" role="3uHU7w">
                  <node concept="3qc1$W" id="2ieUQWSqhlR" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="2ieUQWSqhlS" role="3Sueug">
                    <property role="3cmrfH" value="4" />
                  </node>
                </node>
                <node concept="3SuevK" id="2ieUQWSqhlT" role="3uHU7B">
                  <node concept="3qc1$W" id="2ieUQWSqhlU" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="FJ1c_" id="2ieUQWSqhlV" role="3Sueug">
                    <node concept="3SuevK" id="2ieUQWSqhlW" role="3uHU7w">
                      <node concept="3qc1$W" id="2ieUQWSqhlX" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="2ieUQWSqhlY" role="3Sueug">
                        <property role="3cmrfH" value="64" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="2ieUQWSqhm4" role="3uHU7B">
                      <ref role="3cqZAo" node="7hpWUTz9T1K" resolve="ct3_length" />
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T0i" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0j" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0k" role="3cpWs9">
              <property role="TrG5h" value="ct3_lb_dec" />
              <node concept="10Q1$e" id="7hpWUTz9T0l" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0m" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0n" role="33vP2m">
                <ref role="37wK5l" to="liel:6IcGJgJ0RWl" resolve="aes_gcm_decrypt" />
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <node concept="37vLTw" id="7hpWUTz9T0o" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZE" resolve="tk_shs" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0p" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9SZL" resolve="iv_shs" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0q" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1M" resolve="ct_3_lb" />
                </node>
                <node concept="37vLTw" id="2ieUQWSqiYt" role="37wK5m">
                  <ref role="3cqZAo" node="2ieUQWSqhlN" resolve="num_aes_blocks" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T0r" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0s" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0t" role="3cpWs9">
              <property role="TrG5h" value="preimage_h3" />
              <node concept="10Q1$e" id="7hpWUTz9T0u" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0v" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0w" role="33vP2m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
                <node concept="37vLTw" id="7hpWUTz9T0x" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1C" resolve="pt2" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0y" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0b" resolve="ct3_dec" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T0z" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0$" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0_" role="3cpWs9">
              <property role="TrG5h" value="H_3" />
              <node concept="10Q1$e" id="7hpWUTz9T0A" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0B" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0C" role="33vP2m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:6IcGJgybQ6o" resolve="sha2_of_prefix" />
                <node concept="37vLTw" id="7hpWUTz9T0D" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0t" resolve="preimage_h3" />
                </node>
                <node concept="3cpWs3" id="7hpWUTz9T0E" role="37wK5m">
                  <node concept="37vLTw" id="7hpWUTz9T0F" role="3uHU7B">
                    <ref role="3cqZAo" node="7hpWUTz9T1F" resolve="pt2_len" />
                  </node>
                  <node concept="37vLTw" id="7hpWUTz9T0G" role="3uHU7w">
                    <ref role="3cqZAo" node="7hpWUTz9T1K" resolve="ct3_length" />
                  </node>
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0H" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0k" resolve="ct3_lb_dec" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T0I" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0J" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0K" role="3cpWs9">
              <property role="TrG5h" value="CATS" />
              <node concept="10Q1$e" id="7hpWUTz9T0L" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0M" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0N" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9T0O" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T02" resolve="MS" />
                </node>
                <node concept="Xl_RD" id="7hpWUTz9T0P" role="37wK5m">
                  <property role="Xl_RC" value="c ap traffic" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T0Q" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0_" resolve="H_3" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T0R" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T0S" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T0T" role="3cpWs9">
              <property role="TrG5h" value="tk_capp" />
              <node concept="10Q1$e" id="7hpWUTz9T0U" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T0V" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T0W" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9T0X" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0K" resolve="CATS" />
                </node>
                <node concept="3cmrfG" id="7hpWUTz9T0Y" role="37wK5m">
                  <property role="3cmrfH" value="16" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWs8" id="7hpWUTz9T0Z" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T10" role="3cpWs9">
              <property role="TrG5h" value="iv_capp" />
              <node concept="10Q1$e" id="7hpWUTz9T11" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T12" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T13" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="7hpWUTz9T14" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0K" resolve="CATS" />
                </node>
                <node concept="3cmrfG" id="7hpWUTz9T15" role="37wK5m">
                  <property role="3cmrfH" value="12" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T16" role="3cqZAp" />
          <node concept="3cpWs8" id="7hpWUTz9T17" role="3cqZAp">
            <node concept="3cpWsn" id="7hpWUTz9T18" role="3cpWs9">
              <property role="TrG5h" value="dns_plaintext" />
              <node concept="10Q1$e" id="7hpWUTz9T19" role="1tU5fm">
                <node concept="3qc1$W" id="7hpWUTz9T1a" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="7hpWUTz9T1b" role="33vP2m">
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
                <node concept="37vLTw" id="7hpWUTz9T1c" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T0T" resolve="tk_capp" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T1d" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T10" resolve="iv_capp" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T1e" role="37wK5m">
                  <ref role="3cqZAo" node="7hpWUTz9T1P" resolve="dns_ciphertext" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="7hpWUTz9T1f" role="3cqZAp" />
          <node concept="3cpWs6" id="7hpWUTz9T1g" role="3cqZAp">
            <node concept="2ShNRf" id="7hpWUTz9T1h" role="3cqZAk">
              <node concept="3g6Rrh" id="7hpWUTz9T1i" role="2ShVmc">
                <node concept="10Q1$e" id="7hpWUTz9T1j" role="3g7fb8">
                  <node concept="3qc1$W" id="7hpWUTz9T1k" role="10Q1$1">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
                <node concept="37vLTw" id="7hpWUTz9T1l" role="3g7hyw">
                  <ref role="3cqZAo" node="7hpWUTz9T18" resolve="dns_plaintext" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T1m" role="3g7hyw">
                  <ref role="3cqZAo" node="7hpWUTz9T0T" resolve="tk_capp" />
                </node>
                <node concept="37vLTw" id="7hpWUTz9T1n" role="3g7hyw">
                  <ref role="3cqZAo" node="7hpWUTz9T10" resolve="iv_capp" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="10Q1$e" id="7hpWUTz9T1o" role="3clF45">
          <node concept="10Q1$e" id="7hpWUTz9T1p" role="10Q1$1">
            <node concept="3qc1$W" id="7hpWUTz9T1q" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9URm" role="3clF46">
          <property role="TrG5h" value="PSK" />
          <node concept="10Q1$e" id="7hpWUTz9VfI" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9VeK" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1r" role="3clF46">
          <property role="TrG5h" value="ecdhe_sk" />
          <node concept="3qc1$W" id="7hpWUTz9T1s" role="1tU5fm">
            <property role="3qc1Xj" value="256" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1t" role="3clF46">
          <property role="TrG5h" value="A_x" />
          <node concept="2D7PWU" id="7hpWUTz9T1u" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1v" role="3clF46">
          <property role="TrG5h" value="A_y" />
          <node concept="2D7PWU" id="7hpWUTz9T1w" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1x" role="3clF46">
          <property role="TrG5h" value="B_x" />
          <node concept="2D7PWU" id="7hpWUTz9T1y" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1z" role="3clF46">
          <property role="TrG5h" value="B_y" />
          <node concept="2D7PWU" id="7hpWUTz9T1$" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1_" role="3clF46">
          <property role="TrG5h" value="H_2" />
          <node concept="10Q1$e" id="7hpWUTz9T1A" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9T1B" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1C" role="3clF46">
          <property role="TrG5h" value="pt2" />
          <node concept="10Q1$e" id="7hpWUTz9T1D" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9T1E" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1F" role="3clF46">
          <property role="TrG5h" value="pt2_len" />
          <node concept="3qc1$W" id="7hpWUTz9T1G" role="1tU5fm">
            <property role="3qc1Xj" value="16" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1H" role="3clF46">
          <property role="TrG5h" value="ct_3" />
          <node concept="10Q1$e" id="7hpWUTz9T1I" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9T1J" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1K" role="3clF46">
          <property role="TrG5h" value="ct3_length" />
          <node concept="3qc1$W" id="7hpWUTz9T1L" role="1tU5fm">
            <property role="3qc1Xj" value="16" />
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1M" role="3clF46">
          <property role="TrG5h" value="ct_3_lb" />
          <node concept="10Q1$e" id="7hpWUTz9T1N" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9T1O" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="7hpWUTz9T1P" role="3clF46">
          <property role="TrG5h" value="dns_ciphertext" />
          <node concept="10Q1$e" id="7hpWUTz9T1Q" role="1tU5fm">
            <node concept="3qc1$W" id="7hpWUTz9T1R" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="3Tm1VV" id="7hpWUTz9T1S" role="1B3o_S" />
      </node>
    </node>
    <node concept="2tJIrI" id="64TdDNHMZuZ" role="jymVt" />
    <node concept="2tJIrI" id="6IcGJgIXwEK" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dAwEk" role="jymVt">
      <property role="DRO8Q" value="Implements the HS shortcut, where the client's witness is the HS secret " />
    </node>
    <node concept="DJdLC" id="30zMb0dAyRb" role="jymVt">
      <property role="DRO8Q" value="Steps:" />
    </node>
    <node concept="DJdLC" id="30zMb0dAzFL" role="jymVt">
      <property role="DRO8Q" value="(1) Derive the server handshake key using the HS" />
    </node>
    <node concept="DJdLC" id="30zMb0dBlR3" role="jymVt">
      <property role="DRO8Q" value="(2) Use it to decrypt the ServerFinished value from the transcript - real_SF" />
    </node>
    <node concept="DJdLC" id="30zMb0dBj5j" role="jymVt">
      <property role="DRO8Q" value="(3) Derive the ServerFinished value using the purported HS - calculated_SF" />
    </node>
    <node concept="DJdLC" id="30zMb0dBlm3" role="jymVt">
      <property role="DRO8Q" value="(4) Verify that the two SF values are the same" />
    </node>
    <node concept="DJdLC" id="30zMb0dBms7" role="jymVt">
      <property role="DRO8Q" value="(5) Using the HS, compute the client traffic keys and decrypt the ciphertext" />
    </node>
    <node concept="2tJIrI" id="30zMb0dB$Os" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dHxpZ" role="jymVt">
      <property role="DRO8Q" value="HS - handshake secret" />
    </node>
    <node concept="DJdLC" id="30zMb0dHxN1" role="jymVt">
      <property role="DRO8Q" value="H2 - Hash(CH || SH)" />
    </node>
    <node concept="DJdLC" id="30zMb0dHyc5" role="jymVt">
      <property role="DRO8Q" value="ServExt - server extensions (the last 36 bytes of which are the ServerFinished ext)" />
    </node>
    <node concept="DJdLC" id="30zMb0dHyKz" role="jymVt">
      <property role="DRO8Q" value="ServExt_tail - the suffix of ServExt that does not fit in a whole SHA block" />
    </node>
    <node concept="2tJIrI" id="30zMb0dHz7S" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dHsXf" role="jymVt">
      <property role="DRO8Q" value="Transcript TR3 = ClientHello || ServerHello || ServExt" />
    </node>
    <node concept="DJdLC" id="30zMb0dHvYl" role="jymVt">
      <property role="DRO8Q" value="note that the final 36 bytes of TR3 contain the ServerFinished extension" />
    </node>
    <node concept="DJdLC" id="30zMb0dHwEW" role="jymVt">
      <property role="DRO8Q" value="TR7 is TR3 without the SF extension; that is, TR7 is TR3 without the last 36 bytes" />
    </node>
    <node concept="2tJIrI" id="30zMb0dHGTy" role="jymVt" />
    <node concept="DJdLC" id="30zMb0dHzA9" role="jymVt">
      <property role="DRO8Q" value="SHA_H_Checkpoint - the H-state of SHA up to the last whole block of TR7" />
    </node>
    <node concept="2YIFZL" id="7L_Qkl0g6pj" role="jymVt">
      <property role="TrG5h" value="get1RTT_HS_new" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="7L_Qkl0g6pk" role="3clF47">
        <node concept="3clFbH" id="OcUYtiL6AF" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6pm" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6pn" role="3cpWs9">
            <property role="TrG5h" value="SHTS" />
            <node concept="10Q1$e" id="7L_Qkl0g6po" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6pp" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6pq" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="7L_Qkl0g6pr" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tw" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="7L_Qkl0g6ps" role="37wK5m">
                <property role="Xl_RC" value="s hs traffic" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6pt" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tz" resolve="H2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dI77x" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dI77z" role="3SKWNk">
            <property role="3SKdUp" value="traffic key and iv for &quot;server handshake&quot; messages" />
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6pv" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6pw" role="3cpWs9">
            <property role="TrG5h" value="tk_shs" />
            <node concept="10Q1$e" id="7L_Qkl0g6px" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6py" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6pz" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6p$" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pn" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="7L_Qkl0g6p_" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6pA" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6pB" role="3cpWs9">
            <property role="TrG5h" value="iv_shs" />
            <node concept="10Q1$e" id="7L_Qkl0g6pC" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6pD" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6pE" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6pF" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pn" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="7L_Qkl0g6pG" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtl3Zwb" role="3cqZAp" />
        <node concept="3SKdUt" id="OcUYtl40UL" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl40UN" role="3SKWNk">
            <property role="3SKdUp" value="TODO: check if I can deep copy iv_shs last byte instead of xoring 2 times" />
          </node>
        </node>
        <node concept="1X3_iC" id="3w260cz8G_g" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3cpWs8" id="3w260cys9Td" role="8Wnug">
            <node concept="3cpWsn" id="3w260cys9Tg" role="3cpWs9">
              <property role="TrG5h" value="xored" />
              <node concept="10Q1$e" id="3w260cysany" role="1tU5fm">
                <node concept="3qc1$W" id="3w260cys9Tb" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2OqwBi" id="3w260cyZPhx" role="33vP2m">
                <node concept="37vLTw" id="3w260cysawg" role="2Oq$k0">
                  <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                </node>
                <node concept="2SEQ$1" id="3w260cyZPPg" role="2OqNvi" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="3w260cyqxoQ" role="3cqZAp">
          <node concept="3SKdUq" id="3w260cyqxoS" role="3SKWNk">
            <property role="3SKdUp" value="XOR original IV with the packet number (eiter 0x02 or 0x03)" />
          </node>
        </node>
        <node concept="3clFbF" id="3w260cydbOK" role="3cqZAp">
          <node concept="37vLTI" id="3w260cydigq" role="3clFbG">
            <node concept="pVQyQ" id="3w260cydjjQ" role="37vLTx">
              <node concept="2nou5x" id="3w260cydjpm" role="3uHU7w">
                <property role="2noCCI" value="02" />
              </node>
              <node concept="AH0OO" id="3w260cydiBJ" role="3uHU7B">
                <node concept="3cpWsd" id="3w260cydj1d" role="AHEQo">
                  <node concept="3cmrfG" id="3w260cydj1q" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="3w260cydiMF" role="3uHU7B">
                    <node concept="37vLTw" id="3w260cydiIj" role="2Oq$k0">
                      <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="3w260cydiSf" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="3w260cydiyZ" role="AHHXb">
                  <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="3w260cydccP" role="37vLTJ">
              <node concept="3cpWsd" id="3w260cydhPM" role="AHEQo">
                <node concept="3cmrfG" id="3w260cydhPZ" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="3w260cyddwX" role="3uHU7B">
                  <node concept="37vLTw" id="3w260cydctQ" role="2Oq$k0">
                    <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="3w260cydhuU" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="3w260cz4eyV" role="AHHXb">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtl3VWX" role="3cqZAp" />
        <node concept="3SKdUt" id="OcUYtl3Vi9" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl3Vib" role="3SKWNk">
            <property role="3SKdUp" value="TODO: consider switching to TR3_len directly" />
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6pO" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6pP" role="3cpWs9">
            <property role="TrG5h" value="TR7_len" />
            <node concept="3qc1$W" id="7L_Qkl0g6pQ" role="1tU5fm">
              <property role="3qc1Xj" value="16" />
            </node>
            <node concept="3cpWsd" id="7L_Qkl0g6pR" role="33vP2m">
              <node concept="37vLTw" id="7L_Qkl0g6pS" role="3uHU7B">
                <ref role="3cqZAo" node="7L_Qkl0g6tI" resolve="TR3_len" />
              </node>
              <node concept="3SuevK" id="7L_Qkl0g6pT" role="3uHU7w">
                <node concept="3qc1$W" id="7L_Qkl0g6pU" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="7L_Qkl0g6pV" role="3Sueug">
                  <property role="3cmrfH" value="36" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2lzb9gKdvbz" role="3cqZAp" />
        <node concept="3clFbH" id="2lzb9gKdt4_" role="3cqZAp" />
        <node concept="3SKdUt" id="OcUYtl42gG" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl42gI" role="3SKWNk">
            <property role="3SKdUp" value="TODO: understand if this can be done outside the circuit" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dHLtg" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dHLti" role="3SKWNk">
            <property role="3SKdUp" value="CertVerify = CertVerify_head || CertVerify_tail" />
          </node>
        </node>
        <node concept="3cpWs8" id="7LvGvKRVAzw" role="3cqZAp">
          <node concept="3cpWsn" id="7LvGvKRVAzz" role="3cpWs9">
            <property role="TrG5h" value="CertVerify_head_len" />
            <node concept="3qc1$W" id="7LvGvKRVAzu" role="1tU5fm">
              <property role="3qc1Xj" value="16" />
            </node>
            <node concept="3SuevK" id="7LvGvKRVB44" role="33vP2m">
              <node concept="3qc1$W" id="7LvGvKRVB46" role="3SuevR">
                <property role="3qc1Xj" value="16" />
              </node>
              <node concept="3cpWsd" id="7LvGvKRVC0g" role="3Sueug">
                <node concept="37vLTw" id="7LvGvKRVDhg" role="3uHU7w">
                  <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
                </node>
                <node concept="37vLTw" id="3w260cyqyS4" role="3uHU7B">
                  <ref role="3cqZAo" node="3w260cyqwlA" resolve="CertVerify_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7LvGvKRVRcn" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dHN2_" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dHN2B" role="3SKWNk">
            <property role="3SKdUp" value="To decrypt the tail, we need to calculate the GCM counter block number" />
          </node>
        </node>
        <node concept="3cpWs8" id="7LvGvKRVMP_" role="3cqZAp">
          <node concept="3cpWsn" id="7LvGvKRVMPC" role="3cpWs9">
            <property role="TrG5h" value="gcm_block_number" />
            <node concept="3qc1$W" id="7LvGvKRVMPz" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3SuevK" id="7LvGvKRVNny" role="33vP2m">
              <node concept="3qc1$W" id="7LvGvKRVNn$" role="3SuevR">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="FJ1c_" id="7LvGvKRVQF9" role="3Sueug">
                <node concept="3SuevK" id="7LvGvKRVQFU" role="3uHU7w">
                  <node concept="3qc1$W" id="7LvGvKRVQFW" role="3SuevR">
                    <property role="3qc1Xj" value="16" />
                  </node>
                  <node concept="3cmrfG" id="7LvGvKRVQIa" role="3Sueug">
                    <property role="3cmrfH" value="16" />
                  </node>
                </node>
                <node concept="37vLTw" id="7LvGvKRVQb5" role="3uHU7B">
                  <ref role="3cqZAo" node="7LvGvKRVAzz" resolve="CertVerify_head_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dHOv9" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dHOvb" role="3SKWNk">
            <property role="3SKdUp" value="Additionally, the tail might not start perfectly at the start of a block" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dHPV3" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dHPV4" role="3SKWNk">
            <property role="3SKdUp" value="That is, the length of head may not be a multiple of 16" />
          </node>
        </node>
        <node concept="3cpWs8" id="7LvGvKRVS52" role="3cqZAp">
          <node concept="3cpWsn" id="7LvGvKRVS55" role="3cpWs9">
            <property role="TrG5h" value="offset" />
            <node concept="3qc1$W" id="7LvGvKRVS50" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3SuevK" id="7LvGvKRVSBq" role="33vP2m">
              <node concept="3qc1$W" id="7LvGvKRVSBs" role="3SuevR">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="2dk9JS" id="7LvGvKRVTy5" role="3Sueug">
                <node concept="3SuevK" id="7LvGvKRVTzG" role="3uHU7w">
                  <node concept="3qc1$W" id="7LvGvKRVTzI" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="7LvGvKRVTAe" role="3Sueug">
                    <property role="3cmrfH" value="16" />
                  </node>
                </node>
                <node concept="37vLTw" id="7LvGvKRVT02" role="3uHU7B">
                  <ref role="3cqZAo" node="7LvGvKRVAzz" resolve="CertVerify_head_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7LvGvKRVQKZ" role="3cqZAp" />
        <node concept="3SKdUt" id="7L_Qkl0g6qz" role="3cqZAp">
          <node concept="3SKdUq" id="7L_Qkl0g6q$" role="3SKWNk">
            <property role="3SKdUp" value="This function decrypts the tail with the specific GCM block number and offset within the block (VERY CONVENIENT)" />
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6q_" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6qA" role="3cpWs9">
            <property role="TrG5h" value="CertVerify_tail" />
            <node concept="10Q1$e" id="7L_Qkl0g6qB" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6qC" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="LEx6GKm0kb" role="33vP2m">
              <ref role="37wK5l" to="liel:LEx6GKl5uo" resolve="aes_gcm_decrypt_128bytes_middle" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="3w260cyVqgg" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pw" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="3w260cz8Id7" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="LEx6GKm0ke" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tK" resolve="CertVerify_ct_tail" />
              </node>
              <node concept="37vLTw" id="7LvGvKRVVjV" role="37wK5m">
                <ref role="3cqZAo" node="7LvGvKRVMPC" resolve="gcm_block_number" />
              </node>
              <node concept="37vLTw" id="7LvGvKRVWRu" role="37wK5m">
                <ref role="3cqZAo" node="7LvGvKRVS55" resolve="offset" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="OcUYtl45c1" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl45c3" role="3SKWNk">
            <property role="3SKdUp" value="xoring again for the next record layer" />
          </node>
        </node>
        <node concept="3clFbF" id="3w260cz4eIT" role="3cqZAp">
          <node concept="37vLTI" id="3w260cz4eIU" role="3clFbG">
            <node concept="pVQyQ" id="3w260cz4eIV" role="37vLTx">
              <node concept="2nou5x" id="3w260cz4eIW" role="3uHU7w">
                <property role="2noCCI" value="02" />
              </node>
              <node concept="AH0OO" id="3w260cz4eIX" role="3uHU7B">
                <node concept="3cpWsd" id="3w260cz4eIY" role="AHEQo">
                  <node concept="3cmrfG" id="3w260cz4eIZ" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="3w260cz4eJ0" role="3uHU7B">
                    <node concept="37vLTw" id="3w260cz4eJ1" role="2Oq$k0">
                      <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="3w260cz4eJ2" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="3w260cz4eJ3" role="AHHXb">
                  <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="3w260cz4eJ4" role="37vLTJ">
              <node concept="3cpWsd" id="3w260cz4eJ5" role="AHEQo">
                <node concept="3cmrfG" id="3w260cz4eJ6" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="3w260cz4eJ7" role="3uHU7B">
                  <node concept="37vLTw" id="3w260cz4eJ8" role="2Oq$k0">
                    <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="3w260cz4eJ9" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="3w260cz4eJa" role="AHHXb">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="3w260cysb3K" role="3cqZAp">
          <node concept="37vLTI" id="3w260cysb3L" role="3clFbG">
            <node concept="pVQyQ" id="3w260cysb3M" role="37vLTx">
              <node concept="2nou5x" id="3w260cysb3N" role="3uHU7w">
                <property role="2noCCI" value="03" />
              </node>
              <node concept="AH0OO" id="3w260cysb3O" role="3uHU7B">
                <node concept="3cpWsd" id="3w260cysb3P" role="AHEQo">
                  <node concept="3cmrfG" id="3w260cysb3Q" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="3w260cysb3R" role="3uHU7B">
                    <node concept="37vLTw" id="3w260cysb3S" role="2Oq$k0">
                      <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="3w260cysb3T" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="3w260cysb3U" role="AHHXb">
                  <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="3w260cysb3V" role="37vLTJ">
              <node concept="3cpWsd" id="3w260cysb3W" role="AHEQo">
                <node concept="3cmrfG" id="3w260cysb3X" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="3w260cysb3Y" role="3uHU7B">
                  <node concept="37vLTw" id="3w260cysb3Z" role="2Oq$k0">
                    <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="3w260cysb40" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="3w260cz4g8$" role="AHHXb">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="OcUYtl49yE" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl49yG" role="3SKWNk">
            <property role="3SKdUp" value="Decrypting the FULL serverfinished (easy)" />
          </node>
        </node>
        <node concept="3cpWs8" id="3w260cyrZOl" role="3cqZAp">
          <node concept="3cpWsn" id="3w260cyrZOo" role="3cpWs9">
            <property role="TrG5h" value="ServerFinished" />
            <node concept="10Q1$e" id="3w260cys0h8" role="1tU5fm">
              <node concept="3qc1$W" id="3w260cyrZOj" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="3w260cys7U5" role="33vP2m">
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="3w260cys7U6" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pw" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="3w260cz8G0A" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="3w260cys7U8" role="37wK5m">
                <ref role="3cqZAo" node="3w260cyqup1" resolve="ServerFinished_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5MVebQKEML7" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dBCFk" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dBCFm" role="3SKWNk">
            <property role="3SKdUp" value="This function calculates the hash of TR3 and TR7 where TR7 is TR3 without the last 36 characters" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dBDFP" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dBDFR" role="3SKWNk">
            <property role="3SKdUp" value="starting with the SHA_H_Checkpoint provided as a checkpoint state of SHA that is common to both transcripts." />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dBIUV" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIccU" role="3SKWNk">
            <property role="3SKdUp" value="The inputs are:" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dId19" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dId1b" role="3SKWNk">
            <property role="3SKdUp" value="- the checkpoint state" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dIdV3" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIdV5" role="3SKWNk">
            <property role="3SKdUp" value="- the length of TR3 and TR7 (the latter must be a prefix of the former)" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dIeSp" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIeSr" role="3SKWNk">
            <property role="3SKdUp" value="- the tail of TR3 (the part after the checkpoint)" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dIfMO" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIfMQ" role="3SKWNk">
            <property role="3SKdUp" value="- the length of the tail up to TR3" />
          </node>
        </node>
        <node concept="3SKdUt" id="30zMb0dIgNd" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIgNf" role="3SKWNk">
            <property role="3SKdUp" value="- the length of the tail up to TR7" />
          </node>
        </node>
        <node concept="3cpWs8" id="3w260czbLzM" role="3cqZAp">
          <node concept="3cpWsn" id="3w260czbLzP" role="3cpWs9">
            <property role="TrG5h" value="Decrypted_Merged_tail" />
            <node concept="10Q1$e" id="3w260czbMbc" role="1tU5fm">
              <node concept="3qc1$W" id="3w260czbLzK" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2ShNRf" id="3w260czbNZ$" role="33vP2m">
              <node concept="3$_iS1" id="3w260czbO$T" role="2ShVmc">
                <node concept="3$GHV9" id="3w260czbO$V" role="3$GQph">
                  <node concept="3cmrfG" id="3w260czbP5E" role="3$I4v7">
                    <property role="3cmrfH" value="128" />
                  </node>
                </node>
                <node concept="3qc1$W" id="3w260czbO$S" role="3$_nBY">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="6IcGJgIYT5o" role="3cqZAp">
          <node concept="3cpWsn" id="6IcGJgIYT5p" role="3cpWs9">
            <property role="TrG5h" value="ServFinRam" />
            <property role="3TUv4t" value="false" />
            <node concept="SEaj5" id="6IcGJgIYT5q" role="1tU5fm">
              <node concept="3qc1$W" id="6IcGJgIYT5r" role="SEaiP">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="6IcGJgIYT5s" role="3cqZAp">
          <node concept="37vLTI" id="6IcGJgIYT5t" role="3clFbG">
            <node concept="SEatS" id="6IcGJgIYT5u" role="37vLTx">
              <node concept="3qc1$W" id="6IcGJgIYT5v" role="6EdiW">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="37vLTw" id="3w260czbZtP" role="SEatU">
                <ref role="3cqZAo" node="3w260cyrZOo" resolve="ServerFinished" />
              </node>
            </node>
            <node concept="37vLTw" id="6IcGJgIYT5x" role="37vLTJ">
              <ref role="3cqZAo" node="6IcGJgIYT5p" resolve="ServFinRam" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="3w260czbXLO" role="3cqZAp" />
        <node concept="3SKdUt" id="OcUYtl4dfC" role="3cqZAp">
          <node concept="3SKdUq" id="OcUYtl4dfE" role="3SKWNk">
            <property role="3SKdUp" value="TODO: is it necessary to pad with zeroes?" />
          </node>
        </node>
        <node concept="1Dw8fO" id="3w260czbvV4" role="3cqZAp">
          <node concept="3clFbS" id="3w260czbvV6" role="2LFqv$">
            <node concept="3clFbJ" id="3w260czbKsD" role="3cqZAp">
              <node concept="3clFbS" id="3w260czbKsF" role="3clFbx">
                <node concept="3clFbF" id="3w260czbNa9" role="3cqZAp">
                  <node concept="37vLTI" id="3w260czbQdP" role="3clFbG">
                    <node concept="AH0OO" id="3w260czbQk6" role="37vLTx">
                      <node concept="37vLTw" id="3w260czbQoW" role="AHEQo">
                        <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="3w260czbQiB" role="AHHXb">
                        <ref role="3cqZAo" node="7L_Qkl0g6qA" resolve="CertVerify_tail" />
                      </node>
                    </node>
                    <node concept="AH0OO" id="3w260czbNcL" role="37vLTJ">
                      <node concept="37vLTw" id="3w260czbPM0" role="AHEQo">
                        <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="3w260czbNa7" role="AHHXb">
                        <ref role="3cqZAo" node="3w260czbLzP" resolve="Decrypted_Merged_tail" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eOVzh" id="3w260czbKGC" role="3clFbw">
                <node concept="37vLTw" id="3w260czbKM0" role="3uHU7w">
                  <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
                </node>
                <node concept="3SuevK" id="3w260czbKyn" role="3uHU7B">
                  <node concept="3qc1$W" id="3w260czbKyp" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="37vLTw" id="3w260czbKB0" role="3Sueug">
                    <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                  </node>
                </node>
              </node>
              <node concept="3eNFk2" id="3w260czbQPq" role="3eNLev">
                <node concept="3eOVzh" id="3w260czbRz9" role="3eO9$A">
                  <node concept="3cpWsd" id="3w260czbRpb" role="3uHU7B">
                    <node concept="3SuevK" id="3w260czbRgq" role="3uHU7B">
                      <node concept="3qc1$W" id="3w260czbRgs" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="37vLTw" id="3w260czbRkP" role="3Sueug">
                        <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="3w260czbRtl" role="3uHU7w">
                      <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
                    </node>
                  </node>
                  <node concept="3SuevK" id="3w260czc0vk" role="3uHU7w">
                    <node concept="3qc1$W" id="3w260czc0vm" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="3cmrfG" id="3w260czc0zd" role="3Sueug">
                      <property role="3cmrfH" value="36" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbS" id="3w260czbQPs" role="3eOfB_">
                  <node concept="3clFbF" id="3w260czbRWy" role="3cqZAp">
                    <node concept="37vLTI" id="3w260czbS8k" role="3clFbG">
                      <node concept="AH0OO" id="3w260czbRZa" role="37vLTJ">
                        <node concept="37vLTw" id="3w260czbS2F" role="AHEQo">
                          <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="3w260czbRWx" role="AHHXb">
                          <ref role="3cqZAo" node="3w260czbLzP" resolve="Decrypted_Merged_tail" />
                        </node>
                      </node>
                      <node concept="SwV0n" id="3w260czc1UM" role="37vLTx">
                        <node concept="3cpWsd" id="3w260czc27S" role="SwV0q">
                          <node concept="37vLTw" id="3w260czc2cA" role="3uHU7w">
                            <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
                          </node>
                          <node concept="3SuevK" id="3w260czc1Zs" role="3uHU7B">
                            <node concept="3qc1$W" id="3w260czc1Zu" role="3SuevR">
                              <property role="3qc1Xj" value="8" />
                            </node>
                            <node concept="37vLTw" id="3w260czc23_" role="3Sueug">
                              <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="3w260czc1RM" role="SwV0s">
                          <ref role="3cqZAo" node="6IcGJgIYT5p" resolve="ServFinRam" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eNFk2" id="3w260czc2ws" role="3eNLev">
                <node concept="3eOSWO" id="3w260czc2Zt" role="3eO9$A">
                  <node concept="3cpWs3" id="3w260czc39P" role="3uHU7w">
                    <node concept="3SuevK" id="3w260czc3br" role="3uHU7w">
                      <node concept="3qc1$W" id="3w260czc3bt" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="3w260czc3fD" role="3Sueug">
                        <property role="3cmrfH" value="36" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="3w260czc369" role="3uHU7B">
                      <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
                    </node>
                  </node>
                  <node concept="3SuevK" id="3w260czc2Ig" role="3uHU7B">
                    <node concept="3qc1$W" id="3w260czc2Ii" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="37vLTw" id="3w260czc2LP" role="3Sueug">
                      <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbS" id="3w260czc2wu" role="3eOfB_">
                  <node concept="3clFbF" id="3w260czc3nN" role="3cqZAp">
                    <node concept="37vLTI" id="3w260czc3$T" role="3clFbG">
                      <node concept="3SuevK" id="3w260czc3U$" role="37vLTx">
                        <node concept="3qc1$W" id="3w260czc3UA" role="3SuevR">
                          <property role="3qc1Xj" value="8" />
                        </node>
                        <node concept="3cmrfG" id="3w260czc3Zo" role="3Sueug">
                          <property role="3cmrfH" value="0" />
                        </node>
                      </node>
                      <node concept="AH0OO" id="3w260czc3qs" role="37vLTJ">
                        <node concept="37vLTw" id="3w260czc3sf" role="AHEQo">
                          <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="3w260czc3nM" role="AHHXb">
                          <ref role="3cqZAo" node="3w260czbLzP" resolve="Decrypted_Merged_tail" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="3w260czbvV7" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="3w260czbwy3" role="1tU5fm" />
            <node concept="3cmrfG" id="3w260czbwCU" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="3w260czbI$N" role="1Dwp0S">
            <node concept="3cmrfG" id="3w260czbI_0" role="3uHU7w">
              <property role="3cmrfH" value="128" />
            </node>
            <node concept="37vLTw" id="3w260czbHV3" role="3uHU7B">
              <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="3w260czbJRb" role="1Dwrff">
            <node concept="37vLTw" id="3w260czbJRd" role="2$L3a6">
              <ref role="3cqZAo" node="3w260czbvV7" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="3w260czc4sS" role="3cqZAp">
          <node concept="3cpWsn" id="3w260czc4sV" role="3cpWs9">
            <property role="TrG5h" value="Decrypted_Merged_tail_len" />
            <node concept="3qc1$W" id="3w260czc4sQ" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3cpWs3" id="3w260czcdHU" role="33vP2m">
              <node concept="3SuevK" id="3w260czcegr" role="3uHU7w">
                <node concept="3qc1$W" id="3w260czcegt" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="3w260czceP_" role="3Sueug">
                  <property role="3cmrfH" value="36" />
                </node>
              </node>
              <node concept="37vLTw" id="3w260czcx7r" role="3uHU7B">
                <ref role="3cqZAo" node="7L_Qkl0g6tN" resolve="CertVerify_tail_len" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtl3Om1" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6qJ" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6qK" role="3cpWs9">
            <property role="TrG5h" value="H7_H3" />
            <node concept="10Q1$e" id="7L_Qkl0g6qL" role="1tU5fm">
              <node concept="10Q1$e" id="7L_Qkl0g6qM" role="10Q1$1">
                <node concept="3qc1$W" id="7L_Qkl0g6qN" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
            <node concept="2YIFZM" id="3w260czcxP6" role="33vP2m">
              <ref role="37wK5l" to="d2b1:7L_Qkl0gEu3" resolve="double_sha_from_checkpoint" />
              <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              <node concept="37vLTw" id="3w260czcxP7" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0gHcP" resolve="SHA_H_Checkpoint" />
              </node>
              <node concept="37vLTw" id="3w260czcxP8" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tI" resolve="TR3_len" />
              </node>
              <node concept="37vLTw" id="3w260czcxP9" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pP" resolve="TR7_len" />
              </node>
              <node concept="37vLTw" id="3w260czcxPa" role="37wK5m">
                <ref role="3cqZAo" node="3w260czbLzP" resolve="Decrypted_Merged_tail" />
              </node>
              <node concept="37vLTw" id="3w260czcxPb" role="37wK5m">
                <ref role="3cqZAo" node="3w260czc4sV" resolve="Decrypted_Merged_tail_len" />
              </node>
              <node concept="3cpWsd" id="3w260czcxPc" role="37wK5m">
                <node concept="3SuevK" id="3w260czcxPd" role="3uHU7w">
                  <node concept="3qc1$W" id="3w260czcxPe" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="3w260czcxPf" role="3Sueug">
                    <property role="3cmrfH" value="36" />
                  </node>
                </node>
                <node concept="37vLTw" id="3w260czcxPg" role="3uHU7B">
                  <ref role="3cqZAo" node="3w260czc4sV" resolve="Decrypted_Merged_tail_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6qZ" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6r0" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6r1" role="3cpWs9">
            <property role="TrG5h" value="H_7" />
            <node concept="10Q1$e" id="7L_Qkl0g6r2" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6r3" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="AH0OO" id="7L_Qkl0g6r4" role="33vP2m">
              <node concept="3cmrfG" id="7L_Qkl0g6r5" role="AHEQo">
                <property role="3cmrfH" value="0" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6r6" role="AHHXb">
                <ref role="3cqZAo" node="7L_Qkl0g6qK" resolve="H7_H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6r7" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6r8" role="3cpWs9">
            <property role="TrG5h" value="H_3" />
            <node concept="10Q1$e" id="7L_Qkl0g6r9" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6ra" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="AH0OO" id="7L_Qkl0g6rb" role="33vP2m">
              <node concept="3cmrfG" id="7L_Qkl0g6rc" role="AHEQo">
                <property role="3cmrfH" value="1" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6rd" role="AHHXb">
                <ref role="3cqZAo" node="7L_Qkl0g6qK" resolve="H7_H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="OcUYtl3MeJ" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="1Dw8fO" id="OcUYtj1EFz" role="8Wnug">
            <node concept="3clFbS" id="OcUYtj1EF_" role="2LFqv$">
              <node concept="vCCuG" id="OcUYtj1Jrq" role="3cqZAp">
                <node concept="AH0OO" id="OcUYtj1Jwu" role="vCCWX">
                  <node concept="37vLTw" id="OcUYtj1JyR" role="AHEQo">
                    <ref role="3cqZAo" node="OcUYtj1EFA" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="OcUYtj1Jv5" role="AHHXb">
                    <ref role="3cqZAo" node="7L_Qkl0g6r8" resolve="H_3" />
                  </node>
                </node>
                <node concept="Xl_RD" id="OcUYtj1J_8" role="vCCx3">
                  <property role="Xl_RC" value="Hash from checkpoint" />
                </node>
              </node>
            </node>
            <node concept="3cpWsn" id="OcUYtj1EFA" role="1Duv9x">
              <property role="TrG5h" value="i" />
              <node concept="10Oyi0" id="OcUYtj1Fqn" role="1tU5fm" />
              <node concept="3cmrfG" id="OcUYtj1Fuq" role="33vP2m">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
            <node concept="3eOVzh" id="OcUYtj1FQo" role="1Dwp0S">
              <node concept="2OqwBi" id="OcUYtj1G85" role="3uHU7w">
                <node concept="37vLTw" id="OcUYtj1G2S" role="2Oq$k0">
                  <ref role="3cqZAo" node="7L_Qkl0g6r8" resolve="H_3" />
                </node>
                <node concept="1Rwk04" id="OcUYtj1IOt" role="2OqNvi" />
              </node>
              <node concept="37vLTw" id="OcUYtj1Fwr" role="3uHU7B">
                <ref role="3cqZAo" node="OcUYtj1EFA" resolve="i" />
              </node>
            </node>
            <node concept="3uNrnE" id="OcUYtj1J9q" role="1Dwrff">
              <node concept="37vLTw" id="OcUYtj1J9s" role="2$L3a6">
                <ref role="3cqZAo" node="OcUYtj1EFA" resolve="i" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="OcUYtl3MeK" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3clFbH" id="7L_Qkl0g6re" role="8Wnug" />
        </node>
        <node concept="3SKdUt" id="30zMb0dBKGH" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dBKGJ" role="3SKWNk">
            <property role="3SKdUp" value="Derive the SF value from transcript hash H7 up to Certificate Verify" />
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6rf" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6rg" role="3cpWs9">
            <property role="TrG5h" value="fk_S" />
            <node concept="10Q1$e" id="7L_Qkl0g6rh" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6ri" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6rj" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="7L_Qkl0g6rk" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6pn" resolve="SHTS" />
              </node>
              <node concept="Xl_RD" id="7L_Qkl0g6rl" role="37wK5m">
                <property role="Xl_RC" value="finished" />
              </node>
              <node concept="2ShNRf" id="7L_Qkl0g6rm" role="37wK5m">
                <node concept="3$_iS1" id="7L_Qkl0g6rn" role="2ShVmc">
                  <node concept="3$GHV9" id="7L_Qkl0g6ro" role="3$GQph">
                    <node concept="3cmrfG" id="7L_Qkl0g6rp" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="7L_Qkl0g6rq" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6rr" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6rs" role="3cpWs9">
            <property role="TrG5h" value="SF_calculated" />
            <node concept="10Q1$e" id="7L_Qkl0g6rt" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6ru" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6rv" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6rw" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6rg" resolve="fk_S" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6rx" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6r1" resolve="H_7" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtl3DcB" role="3cqZAp" />
        <node concept="1X3_iC" id="3w260cztrd$" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3SKdUt" id="7L_Qkl0g6r$" role="8Wnug">
            <node concept="3SKdUq" id="7L_Qkl0g6r_" role="3SKWNk">
              <property role="3SKdUp" value="Now, we need to calculate the actual SF value present in the transcript" />
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="3w260cztrd_" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3SKdUt" id="7L_Qkl0g6rA" role="8Wnug">
            <node concept="3SKdUq" id="7L_Qkl0g6rB" role="3SKWNk">
              <property role="3SKdUp" value="We know that SF Verify Data is in the last 32 characters of ServerFinished (i+4 since header is 4 bytes long)" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6rF" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6rG" role="3cpWs9">
            <property role="TrG5h" value="SF_transcript" />
            <node concept="10Q1$e" id="7L_Qkl0g6rH" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6rI" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2ShNRf" id="7L_Qkl0g6rJ" role="33vP2m">
              <node concept="3$_iS1" id="7L_Qkl0g6rK" role="2ShVmc">
                <node concept="3$GHV9" id="7L_Qkl0g6rL" role="3$GQph">
                  <node concept="3cmrfG" id="7L_Qkl0g6rM" role="3$I4v7">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
                <node concept="3qc1$W" id="7L_Qkl0g6rN" role="3$_nBY">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6rP" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6rQ" role="3cpWs9">
            <property role="TrG5h" value="ServerFinished_RAM" />
            <node concept="SEaj5" id="7L_Qkl0g6rR" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6rS" role="SEaiP">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="SEatS" id="7L_Qkl0g6rT" role="33vP2m">
              <node concept="3qc1$W" id="7L_Qkl0g6rU" role="6EdiW">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="37vLTw" id="OcUYtkyPFu" role="SEatU">
                <ref role="3cqZAo" node="3w260cyrZOo" resolve="ServerFinished" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1Dw8fO" id="7L_Qkl0g6rW" role="3cqZAp">
          <node concept="3clFbS" id="7L_Qkl0g6rX" role="2LFqv$">
            <node concept="3clFbF" id="7L_Qkl0g6rY" role="3cqZAp">
              <node concept="37vLTI" id="7L_Qkl0g6rZ" role="3clFbG">
                <node concept="SwV0n" id="7L_Qkl0g6s0" role="37vLTx">
                  <node concept="3cpWs3" id="7L_Qkl0g6s5" role="SwV0q">
                    <node concept="3SuevK" id="7L_Qkl0g6s6" role="3uHU7B">
                      <node concept="3qc1$W" id="7L_Qkl0g6s7" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="37vLTw" id="7L_Qkl0g6s8" role="3Sueug">
                        <ref role="3cqZAo" node="7L_Qkl0g6se" resolve="i" />
                      </node>
                    </node>
                    <node concept="3SuevK" id="OcUYtkyRwF" role="3uHU7w">
                      <node concept="3qc1$W" id="OcUYtkyRwH" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="OcUYtkHpQa" role="3Sueug">
                        <property role="3cmrfH" value="4" />
                      </node>
                    </node>
                  </node>
                  <node concept="37vLTw" id="7L_Qkl0g6sa" role="SwV0s">
                    <ref role="3cqZAo" node="7L_Qkl0g6rQ" resolve="ServerFinished_RAM" />
                  </node>
                </node>
                <node concept="AH0OO" id="7L_Qkl0g6sb" role="37vLTJ">
                  <node concept="37vLTw" id="7L_Qkl0g6sc" role="AHEQo">
                    <ref role="3cqZAo" node="7L_Qkl0g6se" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="7L_Qkl0g6sd" role="AHHXb">
                    <ref role="3cqZAo" node="7L_Qkl0g6rG" resolve="SF_transcript" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="7L_Qkl0g6se" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="7L_Qkl0g6sf" role="1tU5fm" />
            <node concept="3cmrfG" id="7L_Qkl0g6sg" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="7L_Qkl0g6sh" role="1Dwp0S">
            <node concept="37vLTw" id="7L_Qkl0g6si" role="3uHU7B">
              <ref role="3cqZAo" node="7L_Qkl0g6se" resolve="i" />
            </node>
            <node concept="3cmrfG" id="7L_Qkl0g6sj" role="3uHU7w">
              <property role="3cmrfH" value="32" />
            </node>
          </node>
          <node concept="3uNrnE" id="7L_Qkl0g6sk" role="1Dwrff">
            <node concept="37vLTw" id="7L_Qkl0g6sl" role="2$L3a6">
              <ref role="3cqZAo" node="7L_Qkl0g6se" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtjSoed" role="3cqZAp" />
        <node concept="3SKdUt" id="7L_Qkl0g6sn" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dBuwG" role="3SKWNk">
            <property role="3SKdUp" value="Verify that the two SF values are identical" />
          </node>
        </node>
        <node concept="3s6pcg" id="7L_Qkl0g6sp" role="3cqZAp">
          <node concept="2YIFZM" id="7L_Qkl0g6sq" role="3s6pch">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <node concept="37vLTw" id="7L_Qkl0g6sr" role="37wK5m">
              <ref role="3cqZAo" node="7L_Qkl0g6rs" resolve="SF_calculated" />
            </node>
          </node>
          <node concept="2YIFZM" id="7L_Qkl0g6ss" role="3s6pci">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <node concept="37vLTw" id="7L_Qkl0g6st" role="37wK5m">
              <ref role="3cqZAo" node="7L_Qkl0g6rG" resolve="SF_transcript" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="OcUYtjSp3o" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6sw" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6sx" role="3cpWs9">
            <property role="TrG5h" value="dHS" />
            <node concept="10Q1$e" id="7L_Qkl0g6sy" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6sz" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6s$" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="7L_Qkl0g6s_" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tw" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="7L_Qkl0g6sA" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="7L_Qkl0g6sB" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6sC" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6sD" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6sE" role="3cpWs9">
            <property role="TrG5h" value="MS" />
            <node concept="10Q1$e" id="7L_Qkl0g6sF" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6sG" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6sH" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6sI" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6sx" resolve="dHS" />
              </node>
              <node concept="2YIFZM" id="7L_Qkl0g6sJ" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="7L_Qkl0g6sK" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6sL" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6sM" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6sN" role="3cpWs9">
            <property role="TrG5h" value="CATS" />
            <node concept="10Q1$e" id="7L_Qkl0g6sO" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6sP" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6sQ" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6sR" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6sE" resolve="MS" />
              </node>
              <node concept="Xl_RD" id="7L_Qkl0g6sS" role="37wK5m">
                <property role="Xl_RC" value="c ap traffic" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6sT" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6r8" resolve="H_3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6sU" role="3cqZAp" />
        <node concept="3SKdUt" id="30zMb0dIkOz" role="3cqZAp">
          <node concept="3SKdUq" id="30zMb0dIkO_" role="3SKWNk">
            <property role="3SKdUp" value="client application traffic key, iv" />
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6sV" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6sW" role="3cpWs9">
            <property role="TrG5h" value="tk_capp" />
            <node concept="10Q1$e" id="7L_Qkl0g6sX" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6sY" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6sZ" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6t0" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6sN" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="7L_Qkl0g6t1" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="7L_Qkl0g6t2" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6t3" role="3cpWs9">
            <property role="TrG5h" value="iv_capp" />
            <node concept="10Q1$e" id="7L_Qkl0g6t4" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6t5" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6t6" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="7L_Qkl0g6t7" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6sN" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="7L_Qkl0g6t8" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6t9" role="3cqZAp" />
        <node concept="3cpWs8" id="7L_Qkl0g6ta" role="3cqZAp">
          <node concept="3cpWsn" id="7L_Qkl0g6tb" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="7L_Qkl0g6tc" role="1tU5fm">
              <node concept="3qc1$W" id="7L_Qkl0g6td" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="7L_Qkl0g6te" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="7L_Qkl0g6tf" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6sW" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6tg" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6t3" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6th" role="37wK5m">
                <ref role="3cqZAo" node="7L_Qkl0g6tP" resolve="appl_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="7L_Qkl0g6ti" role="3cqZAp" />
        <node concept="3cpWs6" id="7L_Qkl0g6tj" role="3cqZAp">
          <node concept="2ShNRf" id="7L_Qkl0g6tk" role="3cqZAk">
            <node concept="3g6Rrh" id="7L_Qkl0g6tl" role="2ShVmc">
              <node concept="10Q1$e" id="7L_Qkl0g6tm" role="3g7fb8">
                <node concept="3qc1$W" id="7L_Qkl0g6tn" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6to" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6tb" resolve="dns_plaintext" />
              </node>
              <node concept="37vLTw" id="7LvGvKRQ6FZ" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6pw" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="7LvGvKRQ7Re" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6pB" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6tp" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6sW" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6tq" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6t3" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6tr" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6r8" resolve="H_3" />
              </node>
              <node concept="37vLTw" id="7L_Qkl0g6ts" role="3g7hyw">
                <ref role="3cqZAo" node="7L_Qkl0g6rs" resolve="SF_calculated" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="7L_Qkl0g6tt" role="3clF45">
        <node concept="10Q1$e" id="7L_Qkl0g6tu" role="10Q1$1">
          <node concept="3qc1$W" id="7L_Qkl0g6tv" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tw" role="3clF46">
        <property role="TrG5h" value="HS" />
        <node concept="10Q1$e" id="7L_Qkl0g6tx" role="1tU5fm">
          <node concept="3qc1$W" id="7L_Qkl0g6ty" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tz" role="3clF46">
        <property role="TrG5h" value="H2" />
        <node concept="10Q1$e" id="7L_Qkl0g6t$" role="1tU5fm">
          <node concept="3qc1$W" id="7L_Qkl0g6t_" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tI" role="3clF46">
        <property role="TrG5h" value="TR3_len" />
        <node concept="3qc1$W" id="7L_Qkl0g6tJ" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="3w260cyqwlA" role="3clF46">
        <property role="TrG5h" value="CertVerify_len" />
        <node concept="3qc1$W" id="3w260cyqwL2" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tK" role="3clF46">
        <property role="TrG5h" value="CertVerify_ct_tail" />
        <node concept="10Q1$e" id="7L_Qkl0g6tL" role="1tU5fm">
          <node concept="3qc1$W" id="7L_Qkl0g6tM" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="3w260cyqup1" role="3clF46">
        <property role="TrG5h" value="ServerFinished_ct" />
        <node concept="10Q1$e" id="3w260cyquPA" role="1tU5fm">
          <node concept="3qc1$W" id="3w260cyquOq" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tN" role="3clF46">
        <property role="TrG5h" value="CertVerify_tail_len" />
        <node concept="3qc1$W" id="7L_Qkl0g6tO" role="1tU5fm">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0gHcP" role="3clF46">
        <property role="TrG5h" value="SHA_H_Checkpoint" />
        <node concept="10Q1$e" id="7L_Qkl0gH_8" role="1tU5fm">
          <node concept="3qc1$W" id="7L_Qkl0gHzk" role="10Q1$1">
            <property role="3qc1Xj" value="32" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="7L_Qkl0g6tP" role="3clF46">
        <property role="TrG5h" value="appl_ct" />
        <node concept="10Q1$e" id="7L_Qkl0g6tQ" role="1tU5fm">
          <node concept="3qc1$W" id="7L_Qkl0g6tR" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="7L_Qkl0g6tS" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="6yRKGXcbM6Y" role="jymVt" />
    <node concept="2tJIrI" id="7L_Qkl0g686" role="jymVt" />
    <node concept="2tJIrI" id="6IcGJg3k2S3" role="jymVt" />
    <node concept="2tJIrI" id="7hpWUToAe_Y" role="jymVt" />
    <node concept="2tJIrI" id="6IcGJg3k1Zr" role="jymVt" />
    <node concept="2tJIrI" id="6IcGJg3k27g" role="jymVt" />
    <node concept="2tJIrI" id="64TdDNFQhrj" role="jymVt" />
    <node concept="2tJIrI" id="7LvGvKQuQ$d" role="jymVt" />
  </node>
  <node concept="312cEu" id="5vNIVtZh3Ov">
    <property role="TrG5h" value="Test_TLSKeySchedule" />
    <node concept="2tJIrI" id="5vNIVtZh3Ow" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3Ox" role="jymVt">
      <property role="DRO8Q" value="NOTATION is from https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="3Tm1VV" id="5vNIVtZh3Oy" role="1B3o_S" />
    <node concept="2tJIrI" id="5vNIVtZh3Oz" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3O$" role="jymVt">
      <property role="DRO8Q" value="This class contains functions that compute the different types of TLS1.3 Key Schedule" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3O_" role="jymVt">
      <property role="DRO8Q" value="Input: " />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OA" role="jymVt">
      <property role="DRO8Q" value="  - handshake transcript" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OB" role="jymVt">
      <property role="DRO8Q" value="  - client's secrets (PSK and/or DHE share)" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OC" role="jymVt">
      <property role="DRO8Q" value="  - application data ciphertext" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OD" role="jymVt">
      <property role="DRO8Q" value="Output:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OE" role="jymVt">
      <property role="DRO8Q" value="  - client's application traffic key" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OF" role="jymVt">
      <property role="DRO8Q" value="  - decryption of the applicaton data" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OG" role="jymVt">
      <property role="DRO8Q" value="." />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OH" role="jymVt">
      <property role="DRO8Q" value="This is done for 4 types of TLS 1.3 Key Schedule methods:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OI" role="jymVt">
      <property role="DRO8Q" value="  - 0RTT" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OJ" role="jymVt">
      <property role="DRO8Q" value="  - Baseline 1RTT" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OK" role="jymVt">
      <property role="DRO8Q" value="  - Shortcut 1RTT" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OL" role="jymVt">
      <property role="DRO8Q" value="  - Amortized Opening" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3OM" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3ON" role="jymVt">
      <property role="DRO8Q" value="The notation for all variables in this class is from:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OO" role="jymVt">
      <property role="DRO8Q" value="https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3OP" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3OQ" role="jymVt">
      <property role="DRO8Q" value="The key dervation process for the different methods is in Figure 2" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3OR" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh3OS" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh3OT" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh3OU" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3OV" role="jymVt">
      <property role="DRO8Q" value="0RTT method is a &quot;session resumption&quot; feature offered by TLS" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OW" role="jymVt">
      <property role="DRO8Q" value="where the client and server share a PSK (established in a previous session)" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OX" role="jymVt">
      <property role="DRO8Q" value="and the PSK can be used to send &quot;early data&quot; in the client's first message " />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OY" role="jymVt">
      <property role="DRO8Q" value="without a full handshake" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3OZ" role="jymVt">
      <property role="DRO8Q" value="See Figure 2 from https://eprint.iacr.org/2020/1044.pdf" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3P0" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3P1" role="jymVt">
      <property role="DRO8Q" value="The function broadly does the following steps:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3P2" role="jymVt">
      <property role="DRO8Q" value="(1) Using the PSK and transcript hashes, compute the binder" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3P3" role="jymVt">
      <property role="DRO8Q" value="(2) Verify that it is equal to the REAL_BINDER from the transcript" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3P4" role="jymVt">
      <property role="DRO8Q" value="(3) Now, compute the traffic keys and decrypt the ciphertext" />
    </node>
    <node concept="2YIFZL" id="5vNIVtZh3P5" role="jymVt">
      <property role="TrG5h" value="get0RTT" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5vNIVtZh3P6" role="3clF47">
        <node concept="3clFbH" id="5vNIVtZh3P7" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3P8" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3P9" role="3cpWs9">
            <property role="TrG5h" value="ES" />
            <node concept="10Q1$e" id="5vNIVtZh3Pa" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Pb" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Pc" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <node concept="2YIFZM" id="5vNIVtZh3Pd" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="5vNIVtZh3Pe" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Pf" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3QC" resolve="PSK" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Pg" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Ph" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Pi" role="3cpWs9">
            <property role="TrG5h" value="dES" />
            <node concept="10Q1$e" id="5vNIVtZh3Pj" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Pk" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Pl" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Pm" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3P9" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3Pn" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Po" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Pp" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Pq" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Pr" role="3cpWs9">
            <property role="TrG5h" value="BK" />
            <node concept="10Q1$e" id="5vNIVtZh3Ps" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Pt" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Pu" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Pv" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3P9" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3Pw" role="37wK5m">
                <property role="Xl_RC" value="res binder" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Px" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Py" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Pz" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3P$" role="3cpWs9">
            <property role="TrG5h" value="fk_B" />
            <node concept="10Q1$e" id="5vNIVtZh3P_" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3PA" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3PB" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3PC" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Pr" resolve="BK" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3PD" role="37wK5m">
                <property role="Xl_RC" value="finished" />
              </node>
              <node concept="2ShNRf" id="5vNIVtZh3PE" role="37wK5m">
                <node concept="3$_iS1" id="5vNIVtZh3PF" role="2ShVmc">
                  <node concept="3$GHV9" id="5vNIVtZh3PG" role="3$GQph">
                    <node concept="3cmrfG" id="5vNIVtZh3PH" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="5vNIVtZh3PI" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3PJ" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3PK" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3PL" role="3SKWNk">
            <property role="3SKdUp" value="This is the binder derived by the purported PSK that was given as a witness to the circuit" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3PM" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3PN" role="3cpWs9">
            <property role="TrG5h" value="derived_binder" />
            <node concept="10Q1$e" id="5vNIVtZh3PO" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3PP" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3PQ" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
              <node concept="37vLTw" id="5vNIVtZh3PR" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3P$" resolve="fk_B" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3PS" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3QI" resolve="H_5" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3PT" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3PU" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3PV" role="3SKWNk">
            <property role="3SKdUp" value="Verify that the derived binder is the same as the one from the transcript" />
          </node>
        </node>
        <node concept="3s6pcg" id="5vNIVtZh3PW" role="3cqZAp">
          <node concept="2YIFZM" id="5vNIVtZh3PX" role="3s6pch">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <node concept="37vLTw" id="5vNIVtZh3PY" role="37wK5m">
              <ref role="3cqZAo" node="5vNIVtZh3QL" resolve="REAL_BINDER" />
            </node>
          </node>
          <node concept="2YIFZM" id="5vNIVtZh3PZ" role="3s6pci">
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <node concept="37vLTw" id="5vNIVtZh3Q0" role="37wK5m">
              <ref role="3cqZAo" node="5vNIVtZh3PN" resolve="derived_binder" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Q1" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Q2" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Q3" role="3cpWs9">
            <property role="TrG5h" value="ETS" />
            <node concept="10Q1$e" id="5vNIVtZh3Q4" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Q5" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Q6" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Q7" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3P9" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3Q8" role="37wK5m">
                <property role="Xl_RC" value="c e traffic" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Q9" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3QF" resolve="H_1" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Qa" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Qb" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Qc" role="3cpWs9">
            <property role="TrG5h" value="tk_early" />
            <node concept="10Q1$e" id="5vNIVtZh3Qd" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Qe" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Qf" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Qg" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Q3" resolve="ETS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3Qh" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Qi" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Qj" role="3cpWs9">
            <property role="TrG5h" value="iv_early" />
            <node concept="10Q1$e" id="5vNIVtZh3Qk" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Ql" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Qm" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Qn" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Q3" resolve="ETS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3Qo" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Qp" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Qq" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Qr" role="3SKWNk">
            <property role="3SKdUp" value="decrypt the plaintext" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Qs" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Qt" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="5vNIVtZh3Qu" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Qv" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Qw" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="5vNIVtZh3Qx" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Qc" resolve="tk_early" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Qy" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Qj" resolve="iv_early" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Qz" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3QO" resolve="dns_ciphertext" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="5vNIVtZh3Q$" role="3cqZAp">
          <node concept="37vLTw" id="5vNIVtZh3Q_" role="3cqZAk">
            <ref role="3cqZAo" node="5vNIVtZh3Qt" resolve="dns_plaintext" />
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="5vNIVtZh3QA" role="3clF45">
        <node concept="3qc1$W" id="5vNIVtZh3QB" role="10Q1$1">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3QC" role="3clF46">
        <property role="TrG5h" value="PSK" />
        <node concept="10Q1$e" id="5vNIVtZh3QD" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3QE" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3QF" role="3clF46">
        <property role="TrG5h" value="H_1" />
        <node concept="10Q1$e" id="5vNIVtZh3QG" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3QH" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3QI" role="3clF46">
        <property role="TrG5h" value="H_5" />
        <node concept="10Q1$e" id="5vNIVtZh3QJ" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3QK" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3QL" role="3clF46">
        <property role="TrG5h" value="REAL_BINDER" />
        <node concept="10Q1$e" id="5vNIVtZh3QM" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3QN" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3QO" role="3clF46">
        <property role="TrG5h" value="dns_ciphertext" />
        <node concept="10Q1$e" id="5vNIVtZh3QP" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3QQ" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5vNIVtZh3QR" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3QS" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3QT" role="jymVt">
      <property role="DRO8Q" value="This is the baseline 1RTT handshake key derivation" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QU" role="jymVt">
      <property role="DRO8Q" value="Steps:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QV" role="jymVt">
      <property role="DRO8Q" value="(1) Verify and derive the EC DHE secret" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QW" role="jymVt">
      <property role="DRO8Q" value="(2) Compute server handshake keys" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QX" role="jymVt">
      <property role="DRO8Q" value="(3) Decrypt the encrypted parts of CT3 (CH || SH || ServExt) to get TR3" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QY" role="jymVt">
      <property role="DRO8Q" value="(3) Hash TR3" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3QZ" role="jymVt">
      <property role="DRO8Q" value="(5) Derive client traffic keys and decrypt ciphertext" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3R0" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3R1" role="jymVt">
      <property role="DRO8Q" value="Inputs: DHE share and public points A and B" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3R2" role="jymVt">
      <property role="DRO8Q" value="transcript hash H2 = Hash(CH || SH)" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3R3" role="jymVt">
      <property role="DRO8Q" value="CH_SH - Transcript ClientHello || ServerHello and its length" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3R4" role="jymVt">
      <property role="DRO8Q" value="ServExt_ct - the encrypted Server Extensions and its length" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3R5" role="jymVt">
      <property role="DRO8Q" value="ServExt_tail_ct is the part of ServExt_ct that doesn't fit into a whole SHA block" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3R6" role="jymVt">
      <property role="DRO8Q" value="appl_ct - the application data ciphertext" />
    </node>
    <node concept="2YIFZL" id="5vNIVtZh3R7" role="jymVt">
      <property role="TrG5h" value="get1RTT" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5vNIVtZh3R8" role="3clF47">
        <node concept="3clFbH" id="5vNIVtZh3R9" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Ra" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Rb" role="3cpWs9">
            <property role="TrG5h" value="ES" />
            <node concept="10Q1$e" id="5vNIVtZh3Rc" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Rd" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Re" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <node concept="2YIFZM" id="5vNIVtZh3Rf" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="5vNIVtZh3Rg" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Rh" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="5vNIVtZh3Ri" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Rj" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Rk" role="3cpWs9">
            <property role="TrG5h" value="dES" />
            <node concept="10Q1$e" id="5vNIVtZh3Rl" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Rm" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Rn" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3Ro" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Rb" resolve="ES" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3Rp" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Rq" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Rr" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Rs" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Rt" role="3SKWNk">
            <property role="3SKdUp" value="This function's goals:" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3Ru" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Rv" role="3SKWNk">
            <property role="3SKdUp" value="(1) Verify that G^sk = A where G is the generator of secp256" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3Rw" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Rx" role="3SKWNk">
            <property role="3SKdUp" value="(2) Compute B^sk to obtain the DHE secret" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Ry" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Rz" role="3cpWs9">
            <property role="TrG5h" value="DHE" />
            <node concept="10Q1$e" id="5vNIVtZh3R$" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3R_" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3RA" role="33vP2m">
              <ref role="37wK5l" to="rktg:1fN2f79SE7n" resolve="DHExchange" />
              <ref role="1Pybhc" to="rktg:2OJAT4DN4UY" resolve="ECDHE" />
              <node concept="37vLTw" id="5vNIVtZh3RB" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Ul" resolve="Ax" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RC" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Un" resolve="Ay" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RD" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Up" resolve="Bx" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RE" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Ur" resolve="By" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RF" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Uj" resolve="DHE_share" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3RG" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3RH" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3RI" role="3cpWs9">
            <property role="TrG5h" value="HS" />
            <node concept="10Q1$e" id="5vNIVtZh3RJ" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3RK" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3RL" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <node concept="37vLTw" id="5vNIVtZh3RM" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Rk" resolve="dES" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RN" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Rz" resolve="DHE" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3RO" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3RP" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3RQ" role="3cpWs9">
            <property role="TrG5h" value="SHTS" />
            <node concept="10Q1$e" id="5vNIVtZh3RR" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3RS" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3RT" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh3RU" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3RI" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3RV" role="37wK5m">
                <property role="Xl_RC" value="s hs traffic" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3RW" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Ut" resolve="H2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3RX" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3RY" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3RZ" role="3SKWNk">
            <property role="3SKdUp" value="traffic key and iv for &quot;server handshake&quot; messages" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3S0" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3S1" role="3cpWs9">
            <property role="TrG5h" value="tk_shs" />
            <node concept="10Q1$e" id="5vNIVtZh3S2" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3S3" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3S4" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <node concept="37vLTw" id="5vNIVtZh3S5" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3RQ" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3S6" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3S7" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3S8" role="3cpWs9">
            <property role="TrG5h" value="iv_shs" />
            <node concept="10Q1$e" id="5vNIVtZh3S9" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Sa" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Sb" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="5vNIVtZh3Sc" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3RQ" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3Sd" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Se" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Sf" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Sg" role="3cpWs9">
            <property role="TrG5h" value="dHS" />
            <node concept="10Q1$e" id="5vNIVtZh3Sh" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Si" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Sj" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh3Sk" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3RI" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3Sl" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Sm" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Sn" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3So" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Sp" role="3cpWs9">
            <property role="TrG5h" value="MS" />
            <node concept="10Q1$e" id="5vNIVtZh3Sq" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Sr" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Ss" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3St" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Sg" resolve="dHS" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Su" role="37wK5m">
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <node concept="3cmrfG" id="5vNIVtZh3Sv" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Sw" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Sx" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Sy" role="3SKWNk">
            <property role="3SKdUp" value="Decrypt the server extensions with the server's handshake traffic keys" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Sz" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3S$" role="3cpWs9">
            <property role="TrG5h" value="ServExt" />
            <node concept="10Q1$e" id="5vNIVtZh3S_" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3SA" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3SB" role="33vP2m">
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="5vNIVtZh3SC" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3S1" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3SD" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3S8" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3SE" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3U_" resolve="ServExt_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3SF" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3SG" role="3SKWNk">
            <property role="3SKdUp" value="Now, we need to decrypt the ServExt_tail." />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3SH" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3SI" role="3SKWNk">
            <property role="3SKdUp" value="As we are using AES GCM, we need to find the exact block number that the tail starts at." />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3SJ" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3SK" role="3SKWNk">
            <property role="3SKdUp" value="One AES block = 16 bytes" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3SL" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3SM" role="3cpWs9">
            <property role="TrG5h" value="gcm_block_number" />
            <node concept="3qc1$W" id="5vNIVtZh3SN" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="17qRlL" id="5vNIVtZh3SO" role="33vP2m">
              <node concept="3SuevK" id="5vNIVtZh3SP" role="3uHU7w">
                <node concept="3qc1$W" id="5vNIVtZh3SQ" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3SR" role="3Sueug">
                  <property role="3cmrfH" value="4" />
                </node>
              </node>
              <node concept="3SuevK" id="5vNIVtZh3SS" role="3uHU7B">
                <node concept="3qc1$W" id="5vNIVtZh3ST" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="FJ1c_" id="5vNIVtZh3SU" role="3Sueug">
                  <node concept="3SuevK" id="5vNIVtZh3SV" role="3uHU7w">
                    <node concept="3qc1$W" id="5vNIVtZh3SW" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="3cmrfG" id="5vNIVtZh3SX" role="3Sueug">
                      <property role="3cmrfH" value="64" />
                    </node>
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh3SY" role="3uHU7B">
                    <ref role="3cqZAo" node="5vNIVtZh3UC" resolve="ServExt_len" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3SZ" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3T0" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3T1" role="3SKWNk">
            <property role="3SKdUp" value="Returns the decryption starting at the GCM counter " />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3T2" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3T3" role="3cpWs9">
            <property role="TrG5h" value="Serv_Ext_tail" />
            <node concept="10Q1$e" id="5vNIVtZh3T4" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3T5" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3T6" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:6IcGJgJ0RWl" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="5vNIVtZh3T7" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3S1" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3T8" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3S8" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3T9" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3UE" resolve="ServExt_tail_ct" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Ta" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3SM" resolve="gcm_block_number" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Tb" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Tc" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Td" role="3SKWNk">
            <property role="3SKdUp" value="This transcript is CH || SH || ServExt" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Te" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Tf" role="3cpWs9">
            <property role="TrG5h" value="TR3" />
            <node concept="10Q1$e" id="5vNIVtZh3Tg" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Th" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Ti" role="33vP2m">
              <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
              <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
              <node concept="37vLTw" id="5vNIVtZh3Tj" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Uw" resolve="CH_SH" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Tk" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3S$" resolve="ServExt" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Tl" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Tm" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Tn" role="3SKWNk">
            <property role="3SKdUp" value="As we don't know the true length of ServExt, the variable's size is a fixed upper bound" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3To" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Tp" role="3SKWNk">
            <property role="3SKdUp" value="However, we only require a hash of the true transcript, which is a prefix of the variable" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3Tq" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Tr" role="3SKWNk">
            <property role="3SKdUp" value="of length CH_SH_len + ServExt_len" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Ts" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Tt" role="3cpWs9">
            <property role="TrG5h" value="H3" />
            <node concept="10Q1$e" id="5vNIVtZh3Tu" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Tv" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Tw" role="33vP2m">
              <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              <ref role="37wK5l" to="d2b1:6IcGJgybQ6o" resolve="sha2_of_prefix" />
              <node concept="37vLTw" id="5vNIVtZh3Tx" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Tf" resolve="TR3" />
              </node>
              <node concept="3cpWs3" id="5vNIVtZh3Ty" role="37wK5m">
                <node concept="37vLTw" id="5vNIVtZh3Tz" role="3uHU7B">
                  <ref role="3cqZAo" node="5vNIVtZh3Uz" resolve="CH_SH_len" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3T$" role="3uHU7w">
                  <ref role="3cqZAo" node="5vNIVtZh3UC" resolve="ServExt_len" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh3T_" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3T3" resolve="Serv_Ext_tail" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3TA" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3TB" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3TC" role="3cpWs9">
            <property role="TrG5h" value="CATS" />
            <node concept="10Q1$e" id="5vNIVtZh3TD" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3TE" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3TF" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh3TG" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Sp" resolve="MS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3TH" role="37wK5m">
                <property role="Xl_RC" value="c ap traffic" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3TI" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Tt" resolve="H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3TJ" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3TK" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3TL" role="3cpWs9">
            <property role="TrG5h" value="tk_capp" />
            <node concept="10Q1$e" id="5vNIVtZh3TM" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3TN" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3TO" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3TP" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3TC" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3TQ" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3TR" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3TS" role="3cpWs9">
            <property role="TrG5h" value="iv_capp" />
            <node concept="10Q1$e" id="5vNIVtZh3TT" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3TU" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3TV" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="5vNIVtZh3TW" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3TC" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3TX" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3TY" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3TZ" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3U0" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="5vNIVtZh3U1" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3U2" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3U3" role="33vP2m">
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="5vNIVtZh3U4" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3TL" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3U5" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3TS" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3U6" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3UH" resolve="appl_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3U7" role="3cqZAp" />
        <node concept="3cpWs6" id="5vNIVtZh3U8" role="3cqZAp">
          <node concept="2ShNRf" id="5vNIVtZh3U9" role="3cqZAk">
            <node concept="3g6Rrh" id="5vNIVtZh3Ua" role="2ShVmc">
              <node concept="10Q1$e" id="5vNIVtZh3Ub" role="3g7fb8">
                <node concept="3qc1$W" id="5vNIVtZh3Uc" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Ud" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh3U0" resolve="dns_plaintext" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Ue" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh3TL" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Uf" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh3TS" resolve="iv_capp" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="5vNIVtZh3Ug" role="3clF45">
        <node concept="10Q1$e" id="5vNIVtZh3Uh" role="10Q1$1">
          <node concept="3qc1$W" id="5vNIVtZh3Ui" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Uj" role="3clF46">
        <property role="TrG5h" value="DHE_share" />
        <node concept="3qc1$W" id="5vNIVtZh3Uk" role="1tU5fm">
          <property role="3qc1Xj" value="256" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Ul" role="3clF46">
        <property role="TrG5h" value="Ax" />
        <node concept="2D7PWU" id="5vNIVtZh3Um" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Un" role="3clF46">
        <property role="TrG5h" value="Ay" />
        <node concept="2D7PWU" id="5vNIVtZh3Uo" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Up" role="3clF46">
        <property role="TrG5h" value="Bx" />
        <node concept="2D7PWU" id="5vNIVtZh3Uq" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Ur" role="3clF46">
        <property role="TrG5h" value="By" />
        <node concept="2D7PWU" id="5vNIVtZh3Us" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Ut" role="3clF46">
        <property role="TrG5h" value="H2" />
        <node concept="10Q1$e" id="5vNIVtZh3Uu" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3Uv" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Uw" role="3clF46">
        <property role="TrG5h" value="CH_SH" />
        <node concept="10Q1$e" id="5vNIVtZh3Ux" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3Uy" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3Uz" role="3clF46">
        <property role="TrG5h" value="CH_SH_len" />
        <node concept="3qc1$W" id="5vNIVtZh3U$" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3U_" role="3clF46">
        <property role="TrG5h" value="ServExt_ct" />
        <node concept="10Q1$e" id="5vNIVtZh3UA" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3UB" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3UC" role="3clF46">
        <property role="TrG5h" value="ServExt_len" />
        <node concept="3qc1$W" id="5vNIVtZh3UD" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3UE" role="3clF46">
        <property role="TrG5h" value="ServExt_tail_ct" />
        <node concept="10Q1$e" id="5vNIVtZh3UF" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3UG" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh3UH" role="3clF46">
        <property role="TrG5h" value="appl_ct" />
        <node concept="10Q1$e" id="5vNIVtZh3UI" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh3UJ" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5vNIVtZh3UK" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3UL" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh3UM" role="jymVt" />
    <node concept="1X3_iC" id="5vNIVtZh3UN" role="lGtFl">
      <property role="3V$3am" value="member" />
      <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1107461130800/5375687026011219971" />
      <node concept="2YIFZL" id="5vNIVtZh3UO" role="8Wnug">
        <property role="TrG5h" value="get1RTT_resumption" />
        <property role="DiZV1" value="false" />
        <property role="od$2w" value="false" />
        <property role="2aFKle" value="false" />
        <node concept="3clFbS" id="5vNIVtZh3UP" role="3clF47">
          <node concept="3clFbH" id="5vNIVtZh3UQ" role="3cqZAp" />
          <node concept="3clFbH" id="5vNIVtZh3UR" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3US" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3UT" role="3cpWs9">
              <property role="TrG5h" value="ES" />
              <node concept="10Q1$e" id="5vNIVtZh3UU" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3UV" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3UW" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="2YIFZM" id="5vNIVtZh3UX" role="37wK5m">
                  <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <node concept="3cmrfG" id="5vNIVtZh3UY" role="37wK5m">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh3UZ" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XD" resolve="PSK" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3V0" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3V1" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3V2" role="3cpWs9">
              <property role="TrG5h" value="dES" />
              <node concept="10Q1$e" id="5vNIVtZh3V3" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3V4" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3V5" role="33vP2m">
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <node concept="37vLTw" id="5vNIVtZh3V6" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3UT" resolve="ES" />
                </node>
                <node concept="Xl_RD" id="5vNIVtZh3V7" role="37wK5m">
                  <property role="Xl_RC" value="derived" />
                </node>
                <node concept="2YIFZM" id="5vNIVtZh3V8" role="37wK5m">
                  <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                  <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3V9" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Va" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Vb" role="3cpWs9">
              <property role="TrG5h" value="DHE" />
              <node concept="10Q1$e" id="5vNIVtZh3Vc" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Vd" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Ve" role="33vP2m">
                <ref role="37wK5l" to="rktg:1fN2f79SE7n" resolve="DHExchange" />
                <ref role="1Pybhc" to="rktg:2OJAT4DN4UY" resolve="ECDHE" />
                <node concept="37vLTw" id="5vNIVtZh3Vf" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XI" resolve="A_x" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Vg" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XK" resolve="A_y" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Vh" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XM" resolve="B_x" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Vi" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XO" resolve="B_y" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Vj" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XG" resolve="ecdhe_sk" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Vk" role="3cqZAp" />
          <node concept="3clFbH" id="5vNIVtZh3Vl" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Vm" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Vn" role="3cpWs9">
              <property role="TrG5h" value="HS" />
              <node concept="10Q1$e" id="5vNIVtZh3Vo" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Vp" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Vq" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3Vr" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3V2" resolve="dES" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Vs" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Vb" resolve="DHE" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Vt" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Vu" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Vv" role="3cpWs9">
              <property role="TrG5h" value="SHTS" />
              <node concept="10Q1$e" id="5vNIVtZh3Vw" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Vx" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Vy" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3Vz" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Vn" resolve="HS" />
                </node>
                <node concept="Xl_RD" id="5vNIVtZh3V$" role="37wK5m">
                  <property role="Xl_RC" value="s hs traffic" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3V_" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XQ" resolve="H_2" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3VA" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3VB" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3VC" role="3cpWs9">
              <property role="TrG5h" value="tk_shs" />
              <node concept="10Q1$e" id="5vNIVtZh3VD" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3VE" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3VF" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3VG" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Vv" resolve="SHTS" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3VH" role="37wK5m">
                  <property role="3cmrfH" value="16" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWs8" id="5vNIVtZh3VI" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3VJ" role="3cpWs9">
              <property role="TrG5h" value="iv_shs" />
              <node concept="10Q1$e" id="5vNIVtZh3VK" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3VL" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3VM" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3VN" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Vv" resolve="SHTS" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3VO" role="37wK5m">
                  <property role="3cmrfH" value="12" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3VP" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3VQ" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3VR" role="3cpWs9">
              <property role="TrG5h" value="dHS" />
              <node concept="10Q1$e" id="5vNIVtZh3VS" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3VT" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3VU" role="33vP2m">
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <node concept="37vLTw" id="5vNIVtZh3VV" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Vn" resolve="HS" />
                </node>
                <node concept="Xl_RD" id="5vNIVtZh3VW" role="37wK5m">
                  <property role="Xl_RC" value="derived" />
                </node>
                <node concept="2YIFZM" id="5vNIVtZh3VX" role="37wK5m">
                  <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
                  <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3VY" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3VZ" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3W0" role="3cpWs9">
              <property role="TrG5h" value="MS" />
              <node concept="10Q1$e" id="5vNIVtZh3W1" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3W2" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3W3" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3W4" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3VR" resolve="dHS" />
                </node>
                <node concept="2YIFZM" id="5vNIVtZh3W5" role="37wK5m">
                  <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                  <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                  <node concept="3cmrfG" id="5vNIVtZh3W6" role="37wK5m">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3W7" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3W8" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3W9" role="3cpWs9">
              <property role="TrG5h" value="ct3_dec" />
              <node concept="10Q1$e" id="5vNIVtZh3Wa" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Wb" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Wc" role="33vP2m">
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
                <node concept="37vLTw" id="5vNIVtZh3Wd" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3VC" resolve="tk_shs" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3We" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3VJ" resolve="iv_shs" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Wf" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XY" resolve="ct_3" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Wg" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Wh" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Wi" role="3cpWs9">
              <property role="TrG5h" value="num_aes_blocks" />
              <node concept="3qc1$W" id="5vNIVtZh3Wj" role="1tU5fm">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="17qRlL" id="5vNIVtZh3Wk" role="33vP2m">
                <node concept="3SuevK" id="5vNIVtZh3Wl" role="3uHU7w">
                  <node concept="3qc1$W" id="5vNIVtZh3Wm" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="5vNIVtZh3Wn" role="3Sueug">
                    <property role="3cmrfH" value="4" />
                  </node>
                </node>
                <node concept="3SuevK" id="5vNIVtZh3Wo" role="3uHU7B">
                  <node concept="3qc1$W" id="5vNIVtZh3Wp" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="FJ1c_" id="5vNIVtZh3Wq" role="3Sueug">
                    <node concept="3SuevK" id="5vNIVtZh3Wr" role="3uHU7w">
                      <node concept="3qc1$W" id="5vNIVtZh3Ws" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="5vNIVtZh3Wt" role="3Sueug">
                        <property role="3cmrfH" value="64" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="5vNIVtZh3Wu" role="3uHU7B">
                      <ref role="3cqZAo" node="5vNIVtZh3Y1" resolve="ct3_length" />
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Wv" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Ww" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Wx" role="3cpWs9">
              <property role="TrG5h" value="ct3_lb_dec" />
              <node concept="10Q1$e" id="5vNIVtZh3Wy" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Wz" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3W$" role="33vP2m">
                <ref role="37wK5l" to="liel:6IcGJgJ0RWl" resolve="aes_gcm_decrypt" />
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <node concept="37vLTw" id="5vNIVtZh3W_" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3VC" resolve="tk_shs" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3WA" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3VJ" resolve="iv_shs" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3WB" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Y3" resolve="ct_3_lb" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3WC" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Wi" resolve="num_aes_blocks" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3WD" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3WE" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3WF" role="3cpWs9">
              <property role="TrG5h" value="preimage_h3" />
              <node concept="10Q1$e" id="5vNIVtZh3WG" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3WH" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3WI" role="33vP2m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4$NxZ8" resolve="concat" />
                <node concept="37vLTw" id="5vNIVtZh3WJ" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3XT" resolve="pt2" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3WK" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3W9" resolve="ct3_dec" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3WL" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3WM" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3WN" role="3cpWs9">
              <property role="TrG5h" value="H_3" />
              <node concept="10Q1$e" id="5vNIVtZh3WO" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3WP" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3WQ" role="33vP2m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:6IcGJgybQ6o" resolve="sha2_of_prefix" />
                <node concept="37vLTw" id="5vNIVtZh3WR" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3WF" resolve="preimage_h3" />
                </node>
                <node concept="3cpWs3" id="5vNIVtZh3WS" role="37wK5m">
                  <node concept="37vLTw" id="5vNIVtZh3WT" role="3uHU7B">
                    <ref role="3cqZAo" node="5vNIVtZh3XW" resolve="pt2_len" />
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh3WU" role="3uHU7w">
                    <ref role="3cqZAo" node="5vNIVtZh3Y1" resolve="ct3_length" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh3WV" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Wx" resolve="ct3_lb_dec" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3WW" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3WX" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3WY" role="3cpWs9">
              <property role="TrG5h" value="CATS" />
              <node concept="10Q1$e" id="5vNIVtZh3WZ" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3X0" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3X1" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3X2" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3W0" resolve="MS" />
                </node>
                <node concept="Xl_RD" id="5vNIVtZh3X3" role="37wK5m">
                  <property role="Xl_RC" value="c ap traffic" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3X4" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3WN" resolve="H_3" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3X5" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3X6" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3X7" role="3cpWs9">
              <property role="TrG5h" value="tk_capp" />
              <node concept="10Q1$e" id="5vNIVtZh3X8" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3X9" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Xa" role="33vP2m">
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
                <node concept="37vLTw" id="5vNIVtZh3Xb" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3WY" resolve="CATS" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3Xc" role="37wK5m">
                  <property role="3cmrfH" value="16" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWs8" id="5vNIVtZh3Xd" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Xe" role="3cpWs9">
              <property role="TrG5h" value="iv_capp" />
              <node concept="10Q1$e" id="5vNIVtZh3Xf" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Xg" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Xh" role="33vP2m">
                <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
                <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
                <node concept="37vLTw" id="5vNIVtZh3Xi" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3WY" resolve="CATS" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3Xj" role="37wK5m">
                  <property role="3cmrfH" value="12" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Xk" role="3cqZAp" />
          <node concept="3cpWs8" id="5vNIVtZh3Xl" role="3cqZAp">
            <node concept="3cpWsn" id="5vNIVtZh3Xm" role="3cpWs9">
              <property role="TrG5h" value="dns_plaintext" />
              <node concept="10Q1$e" id="5vNIVtZh3Xn" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Xo" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2YIFZM" id="5vNIVtZh3Xp" role="33vP2m">
                <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
                <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
                <node concept="37vLTw" id="5vNIVtZh3Xq" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3X7" resolve="tk_capp" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Xr" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Xe" resolve="iv_capp" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Xs" role="37wK5m">
                  <ref role="3cqZAo" node="5vNIVtZh3Y6" resolve="dns_ciphertext" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3clFbH" id="5vNIVtZh3Xt" role="3cqZAp" />
          <node concept="3cpWs6" id="5vNIVtZh3Xu" role="3cqZAp">
            <node concept="2ShNRf" id="5vNIVtZh3Xv" role="3cqZAk">
              <node concept="3g6Rrh" id="5vNIVtZh3Xw" role="2ShVmc">
                <node concept="10Q1$e" id="5vNIVtZh3Xx" role="3g7fb8">
                  <node concept="3qc1$W" id="5vNIVtZh3Xy" role="10Q1$1">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Xz" role="3g7hyw">
                  <ref role="3cqZAo" node="5vNIVtZh3Xm" resolve="dns_plaintext" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3X$" role="3g7hyw">
                  <ref role="3cqZAo" node="5vNIVtZh3X7" resolve="tk_capp" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3X_" role="3g7hyw">
                  <ref role="3cqZAo" node="5vNIVtZh3Xe" resolve="iv_capp" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="10Q1$e" id="5vNIVtZh3XA" role="3clF45">
          <node concept="10Q1$e" id="5vNIVtZh3XB" role="10Q1$1">
            <node concept="3qc1$W" id="5vNIVtZh3XC" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XD" role="3clF46">
          <property role="TrG5h" value="PSK" />
          <node concept="10Q1$e" id="5vNIVtZh3XE" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3XF" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XG" role="3clF46">
          <property role="TrG5h" value="ecdhe_sk" />
          <node concept="3qc1$W" id="5vNIVtZh3XH" role="1tU5fm">
            <property role="3qc1Xj" value="256" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XI" role="3clF46">
          <property role="TrG5h" value="A_x" />
          <node concept="2D7PWU" id="5vNIVtZh3XJ" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XK" role="3clF46">
          <property role="TrG5h" value="A_y" />
          <node concept="2D7PWU" id="5vNIVtZh3XL" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XM" role="3clF46">
          <property role="TrG5h" value="B_x" />
          <node concept="2D7PWU" id="5vNIVtZh3XN" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XO" role="3clF46">
          <property role="TrG5h" value="B_y" />
          <node concept="2D7PWU" id="5vNIVtZh3XP" role="1tU5fm">
            <ref role="2D7PX4" to="tluv:4RvoraGGpEM" resolve="p256" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XQ" role="3clF46">
          <property role="TrG5h" value="H_2" />
          <node concept="10Q1$e" id="5vNIVtZh3XR" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3XS" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XT" role="3clF46">
          <property role="TrG5h" value="pt2" />
          <node concept="10Q1$e" id="5vNIVtZh3XU" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3XV" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XW" role="3clF46">
          <property role="TrG5h" value="pt2_len" />
          <node concept="3qc1$W" id="5vNIVtZh3XX" role="1tU5fm">
            <property role="3qc1Xj" value="16" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3XY" role="3clF46">
          <property role="TrG5h" value="ct_3" />
          <node concept="10Q1$e" id="5vNIVtZh3XZ" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3Y0" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3Y1" role="3clF46">
          <property role="TrG5h" value="ct3_length" />
          <node concept="3qc1$W" id="5vNIVtZh3Y2" role="1tU5fm">
            <property role="3qc1Xj" value="16" />
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3Y3" role="3clF46">
          <property role="TrG5h" value="ct_3_lb" />
          <node concept="10Q1$e" id="5vNIVtZh3Y4" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3Y5" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="37vLTG" id="5vNIVtZh3Y6" role="3clF46">
          <property role="TrG5h" value="dns_ciphertext" />
          <node concept="10Q1$e" id="5vNIVtZh3Y7" role="1tU5fm">
            <node concept="3qc1$W" id="5vNIVtZh3Y8" role="10Q1$1">
              <property role="3qc1Xj" value="8" />
            </node>
          </node>
        </node>
        <node concept="3Tm1VV" id="5vNIVtZh3Y9" role="1B3o_S" />
      </node>
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3Ya" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh3Yb" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3Yc" role="jymVt">
      <property role="DRO8Q" value="Implements the HS shortcut, where the client's witness is the HS secret " />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yd" role="jymVt">
      <property role="DRO8Q" value="Steps:" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Ye" role="jymVt">
      <property role="DRO8Q" value="(1) Derive the server handshake key using the HS" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yf" role="jymVt">
      <property role="DRO8Q" value="(2) Use it to decrypt the ServerFinished value from the transcript - real_SF" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yg" role="jymVt">
      <property role="DRO8Q" value="(3) Derive the ServerFinished value using the purported HS - calculated_SF" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yh" role="jymVt">
      <property role="DRO8Q" value="(4) Verify that the two SF values are the same" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yi" role="jymVt">
      <property role="DRO8Q" value="(5) Using the HS, compute the client traffic keys and decrypt the ciphertext" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3Yj" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3Yk" role="jymVt">
      <property role="DRO8Q" value="HS - handshake secret" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yl" role="jymVt">
      <property role="DRO8Q" value="H2 - Hash(CH || SH)" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Ym" role="jymVt">
      <property role="DRO8Q" value="ServExt - server extensions (the last 36 bytes of which are the ServerFinished ext)" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yn" role="jymVt">
      <property role="DRO8Q" value="ServExt_tail - the suffix of ServExt that does not fit in a whole SHA block" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3Yo" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3Yp" role="jymVt">
      <property role="DRO8Q" value="Transcript TR3 = ClientHello || ServerHello || ServExt" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yq" role="jymVt">
      <property role="DRO8Q" value="note that the final 36 bytes of TR3 contain the ServerFinished extension" />
    </node>
    <node concept="DJdLC" id="5vNIVtZh3Yr" role="jymVt">
      <property role="DRO8Q" value="TR7 is TR3 without the SF extension; that is, TR7 is TR3 without the last 36 bytes" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh3Ys" role="jymVt" />
    <node concept="DJdLC" id="5vNIVtZh3Yt" role="jymVt">
      <property role="DRO8Q" value="SHA_H_Checkpoint - the H-state of SHA up to the last whole block of TR7" />
    </node>
    <node concept="2YIFZL" id="5vNIVtZh3Yu" role="jymVt">
      <property role="TrG5h" value="get1RTT_HS_new" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5vNIVtZh3Yv" role="3clF47">
        <node concept="3clFbH" id="5vNIVtZh3Yw" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh3Yx" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Yy" role="3cpWs9">
            <property role="TrG5h" value="SHTS" />
            <node concept="10Q1$e" id="5vNIVtZh3Yz" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3Y$" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3Y_" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh3YA" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh460" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh3YB" role="37wK5m">
                <property role="Xl_RC" value="s hs traffic" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh3YC" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh463" resolve="H2" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3YD" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3YE" role="3SKWNk">
            <property role="3SKdUp" value="traffic key and iv for &quot;server handshake&quot; messages" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3YF" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3YG" role="3cpWs9">
            <property role="TrG5h" value="tk_shs" />
            <node concept="10Q1$e" id="5vNIVtZh3YH" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3YI" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3YJ" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh3YK" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Yy" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3YL" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3YM" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3YN" role="3cpWs9">
            <property role="TrG5h" value="iv_shs" />
            <node concept="10Q1$e" id="5vNIVtZh3YO" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh3YP" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh3YQ" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <node concept="37vLTw" id="5vNIVtZh3YR" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Yy" resolve="SHTS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh3YS" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3YT" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3YU" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3YV" role="3SKWNk">
            <property role="3SKdUp" value="TODO: check if I can deep copy iv_shs last byte instead of xoring 2 times" />
          </node>
        </node>
        <node concept="1X3_iC" id="5vNIVtZh3YW" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3cpWs8" id="5vNIVtZh3YX" role="8Wnug">
            <node concept="3cpWsn" id="5vNIVtZh3YY" role="3cpWs9">
              <property role="TrG5h" value="xored" />
              <node concept="10Q1$e" id="5vNIVtZh3YZ" role="1tU5fm">
                <node concept="3qc1$W" id="5vNIVtZh3Z0" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="2OqwBi" id="5vNIVtZh3Z1" role="33vP2m">
                <node concept="37vLTw" id="5vNIVtZh3Z2" role="2Oq$k0">
                  <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                </node>
                <node concept="2SEQ$1" id="5vNIVtZh3Z3" role="2OqNvi" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3Z4" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Z5" role="3SKWNk">
            <property role="3SKdUp" value="XOR original IV with the packet number (eiter 0x02 or 0x03)" />
          </node>
        </node>
        <node concept="3clFbF" id="5vNIVtZh3Z6" role="3cqZAp">
          <node concept="37vLTI" id="5vNIVtZh3Z7" role="3clFbG">
            <node concept="pVQyQ" id="5vNIVtZh3Z8" role="37vLTx">
              <node concept="2nou5x" id="5vNIVtZh3Z9" role="3uHU7w">
                <property role="2noCCI" value="02" />
              </node>
              <node concept="AH0OO" id="5vNIVtZh3Za" role="3uHU7B">
                <node concept="3cpWsd" id="5vNIVtZh3Zb" role="AHEQo">
                  <node concept="3cmrfG" id="5vNIVtZh3Zc" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="5vNIVtZh3Zd" role="3uHU7B">
                    <node concept="37vLTw" id="5vNIVtZh3Ze" role="2Oq$k0">
                      <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="5vNIVtZh3Zf" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh3Zg" role="AHHXb">
                  <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="5vNIVtZh3Zh" role="37vLTJ">
              <node concept="3cpWsd" id="5vNIVtZh3Zi" role="AHEQo">
                <node concept="3cmrfG" id="5vNIVtZh3Zj" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="5vNIVtZh3Zk" role="3uHU7B">
                  <node concept="37vLTw" id="5vNIVtZh3Zl" role="2Oq$k0">
                    <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="5vNIVtZh3Zm" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh3Zn" role="AHHXb">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Zo" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Zp" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3Zq" role="3SKWNk">
            <property role="3SKdUp" value="TODO: consider switching to TR3_len directly" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3Zr" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3Zs" role="3cpWs9">
            <property role="TrG5h" value="TR7_len" />
            <node concept="3qc1$W" id="5vNIVtZh3Zt" role="1tU5fm">
              <property role="3qc1Xj" value="16" />
            </node>
            <node concept="3cpWsd" id="5vNIVtZh3Zu" role="33vP2m">
              <node concept="37vLTw" id="5vNIVtZh3Zv" role="3uHU7B">
                <ref role="3cqZAo" node="5vNIVtZh466" resolve="TR3_len" />
              </node>
              <node concept="3SuevK" id="5vNIVtZh3Zw" role="3uHU7w">
                <node concept="3qc1$W" id="5vNIVtZh3Zx" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh3Zy" role="3Sueug">
                  <property role="3cmrfH" value="36" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3Zz" role="3cqZAp" />
        <node concept="3clFbH" id="5vNIVtZh3Z$" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3Z_" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3ZA" role="3SKWNk">
            <property role="3SKdUp" value="TODO: understand if this can be done outside the circuit" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3ZB" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3ZC" role="3SKWNk">
            <property role="3SKdUp" value="CertVerify = CertVerify_head || CertVerify_tail" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3ZD" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3ZE" role="3cpWs9">
            <property role="TrG5h" value="CertVerify_head_len" />
            <node concept="3qc1$W" id="5vNIVtZh3ZF" role="1tU5fm">
              <property role="3qc1Xj" value="16" />
            </node>
            <node concept="3SuevK" id="5vNIVtZh3ZG" role="33vP2m">
              <node concept="3qc1$W" id="5vNIVtZh3ZH" role="3SuevR">
                <property role="3qc1Xj" value="16" />
              </node>
              <node concept="3cpWsd" id="5vNIVtZh3ZI" role="3Sueug">
                <node concept="37vLTw" id="5vNIVtZh3ZJ" role="3uHU7w">
                  <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
                </node>
                <node concept="37vLTw" id="5vNIVtZh3ZK" role="3uHU7B">
                  <ref role="3cqZAo" node="5vNIVtZh468" resolve="CertVerify_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh3ZL" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh3ZM" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3ZN" role="3SKWNk">
            <property role="3SKdUp" value="To decrypt the tail, we need to calculate the GCM counter block number" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh3ZO" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh3ZP" role="3cpWs9">
            <property role="TrG5h" value="gcm_block_number" />
            <node concept="3qc1$W" id="5vNIVtZh3ZQ" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3SuevK" id="5vNIVtZh3ZR" role="33vP2m">
              <node concept="3qc1$W" id="5vNIVtZh3ZS" role="3SuevR">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="FJ1c_" id="5vNIVtZh3ZT" role="3Sueug">
                <node concept="3SuevK" id="5vNIVtZh3ZU" role="3uHU7w">
                  <node concept="3qc1$W" id="5vNIVtZh3ZV" role="3SuevR">
                    <property role="3qc1Xj" value="16" />
                  </node>
                  <node concept="3cmrfG" id="5vNIVtZh3ZW" role="3Sueug">
                    <property role="3cmrfH" value="16" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh3ZX" role="3uHU7B">
                  <ref role="3cqZAo" node="5vNIVtZh3ZE" resolve="CertVerify_head_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh3ZY" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh3ZZ" role="3SKWNk">
            <property role="3SKdUp" value="Additionally, the tail might not start perfectly at the start of a block" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh400" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh401" role="3SKWNk">
            <property role="3SKdUp" value="That is, the length of head may not be a multiple of 16" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh402" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh403" role="3cpWs9">
            <property role="TrG5h" value="offset" />
            <node concept="3qc1$W" id="5vNIVtZh404" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3SuevK" id="5vNIVtZh405" role="33vP2m">
              <node concept="3qc1$W" id="5vNIVtZh406" role="3SuevR">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="2dk9JS" id="5vNIVtZh407" role="3Sueug">
                <node concept="3SuevK" id="5vNIVtZh408" role="3uHU7w">
                  <node concept="3qc1$W" id="5vNIVtZh409" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="5vNIVtZh40a" role="3Sueug">
                    <property role="3cmrfH" value="16" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh40b" role="3uHU7B">
                  <ref role="3cqZAo" node="5vNIVtZh3ZE" resolve="CertVerify_head_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh40c" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh40d" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh40e" role="3SKWNk">
            <property role="3SKdUp" value="This function decrypts the tail with the specific GCM block number and offset within the block (VERY CONVENIENT)" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh40f" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh40g" role="3cpWs9">
            <property role="TrG5h" value="CertVerify_tail" />
            <node concept="10Q1$e" id="5vNIVtZh40h" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh40i" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh40j" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:LEx6GKl5uo" resolve="aes_gcm_decrypt_128bytes_middle" />
              <node concept="37vLTw" id="5vNIVtZh40k" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3YG" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh40l" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh40m" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh46a" resolve="CertVerify_ct_tail" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh40n" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3ZP" resolve="gcm_block_number" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh40o" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh403" resolve="offset" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh40p" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh40q" role="3SKWNk">
            <property role="3SKdUp" value="xoring again for the next record layer" />
          </node>
        </node>
        <node concept="3clFbF" id="5vNIVtZh40r" role="3cqZAp">
          <node concept="37vLTI" id="5vNIVtZh40s" role="3clFbG">
            <node concept="pVQyQ" id="5vNIVtZh40t" role="37vLTx">
              <node concept="2nou5x" id="5vNIVtZh40u" role="3uHU7w">
                <property role="2noCCI" value="02" />
              </node>
              <node concept="AH0OO" id="5vNIVtZh40v" role="3uHU7B">
                <node concept="3cpWsd" id="5vNIVtZh40w" role="AHEQo">
                  <node concept="3cmrfG" id="5vNIVtZh40x" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="5vNIVtZh40y" role="3uHU7B">
                    <node concept="37vLTw" id="5vNIVtZh40z" role="2Oq$k0">
                      <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="5vNIVtZh40$" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh40_" role="AHHXb">
                  <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="5vNIVtZh40A" role="37vLTJ">
              <node concept="3cpWsd" id="5vNIVtZh40B" role="AHEQo">
                <node concept="3cmrfG" id="5vNIVtZh40C" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="5vNIVtZh40D" role="3uHU7B">
                  <node concept="37vLTw" id="5vNIVtZh40E" role="2Oq$k0">
                    <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="5vNIVtZh40F" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh40G" role="AHHXb">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5vNIVtZh40H" role="3cqZAp">
          <node concept="37vLTI" id="5vNIVtZh40I" role="3clFbG">
            <node concept="pVQyQ" id="5vNIVtZh40J" role="37vLTx">
              <node concept="2nou5x" id="5vNIVtZh40K" role="3uHU7w">
                <property role="2noCCI" value="03" />
              </node>
              <node concept="AH0OO" id="5vNIVtZh40L" role="3uHU7B">
                <node concept="3cpWsd" id="5vNIVtZh40M" role="AHEQo">
                  <node concept="3cmrfG" id="5vNIVtZh40N" role="3uHU7w">
                    <property role="3cmrfH" value="1" />
                  </node>
                  <node concept="2OqwBi" id="5vNIVtZh40O" role="3uHU7B">
                    <node concept="37vLTw" id="5vNIVtZh40P" role="2Oq$k0">
                      <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                    </node>
                    <node concept="1Rwk04" id="5vNIVtZh40Q" role="2OqNvi" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh40R" role="AHHXb">
                  <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                </node>
              </node>
            </node>
            <node concept="AH0OO" id="5vNIVtZh40S" role="37vLTJ">
              <node concept="3cpWsd" id="5vNIVtZh40T" role="AHEQo">
                <node concept="3cmrfG" id="5vNIVtZh40U" role="3uHU7w">
                  <property role="3cmrfH" value="1" />
                </node>
                <node concept="2OqwBi" id="5vNIVtZh40V" role="3uHU7B">
                  <node concept="37vLTw" id="5vNIVtZh40W" role="2Oq$k0">
                    <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
                  </node>
                  <node concept="1Rwk04" id="5vNIVtZh40X" role="2OqNvi" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh40Y" role="AHHXb">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh40Z" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh410" role="3SKWNk">
            <property role="3SKdUp" value="Decrypting the FULL serverfinished (easy)" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh411" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh412" role="3cpWs9">
            <property role="TrG5h" value="ServerFinished" />
            <node concept="10Q1$e" id="5vNIVtZh413" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh414" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh415" role="33vP2m">
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <node concept="37vLTw" id="5vNIVtZh416" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3YG" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh417" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh418" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh46d" resolve="ServerFinished_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh419" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh41a" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41b" role="3SKWNk">
            <property role="3SKdUp" value="This function calculates the hash of TR3 and TR7 where TR7 is TR3 without the last 36 characters" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41c" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41d" role="3SKWNk">
            <property role="3SKdUp" value="starting with the SHA_H_Checkpoint provided as a checkpoint state of SHA that is common to both transcripts." />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41e" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41f" role="3SKWNk">
            <property role="3SKdUp" value="The inputs are:" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41g" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41h" role="3SKWNk">
            <property role="3SKdUp" value="- the checkpoint state" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41i" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41j" role="3SKWNk">
            <property role="3SKdUp" value="- the length of TR3 and TR7 (the latter must be a prefix of the former)" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41k" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41l" role="3SKWNk">
            <property role="3SKdUp" value="- the tail of TR3 (the part after the checkpoint)" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41m" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41n" role="3SKWNk">
            <property role="3SKdUp" value="- the length of the tail up to TR3" />
          </node>
        </node>
        <node concept="3SKdUt" id="5vNIVtZh41o" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41p" role="3SKWNk">
            <property role="3SKdUp" value="- the length of the tail up to TR7" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh41q" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh41r" role="3cpWs9">
            <property role="TrG5h" value="Decrypted_Merged_tail" />
            <node concept="10Q1$e" id="5vNIVtZh41s" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh41t" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2ShNRf" id="5vNIVtZh41u" role="33vP2m">
              <node concept="3$_iS1" id="5vNIVtZh41v" role="2ShVmc">
                <node concept="3$GHV9" id="5vNIVtZh41w" role="3$GQph">
                  <node concept="3cmrfG" id="5vNIVtZh41x" role="3$I4v7">
                    <property role="3cmrfH" value="128" />
                  </node>
                </node>
                <node concept="3qc1$W" id="5vNIVtZh41y" role="3$_nBY">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh41z" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh41$" role="3cpWs9">
            <property role="TrG5h" value="ServFinRam" />
            <property role="3TUv4t" value="false" />
            <node concept="SEaj5" id="5vNIVtZh41_" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh41A" role="SEaiP">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5vNIVtZh41B" role="3cqZAp">
          <node concept="37vLTI" id="5vNIVtZh41C" role="3clFbG">
            <node concept="SEatS" id="5vNIVtZh41D" role="37vLTx">
              <node concept="3qc1$W" id="5vNIVtZh41E" role="6EdiW">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh41F" role="SEatU">
                <ref role="3cqZAo" node="5vNIVtZh412" resolve="ServerFinished" />
              </node>
            </node>
            <node concept="37vLTw" id="5vNIVtZh41G" role="37vLTJ">
              <ref role="3cqZAo" node="5vNIVtZh41$" resolve="ServFinRam" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh41H" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh41I" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh41J" role="3SKWNk">
            <property role="3SKdUp" value="TODO: is it necessary to pad with zeroes?" />
          </node>
        </node>
        <node concept="1Dw8fO" id="5vNIVtZh41K" role="3cqZAp">
          <node concept="3clFbS" id="5vNIVtZh41L" role="2LFqv$">
            <node concept="3clFbJ" id="5vNIVtZh41M" role="3cqZAp">
              <node concept="3clFbS" id="5vNIVtZh41N" role="3clFbx">
                <node concept="3clFbF" id="5vNIVtZh41O" role="3cqZAp">
                  <node concept="37vLTI" id="5vNIVtZh41P" role="3clFbG">
                    <node concept="AH0OO" id="5vNIVtZh41Q" role="37vLTx">
                      <node concept="37vLTw" id="5vNIVtZh41R" role="AHEQo">
                        <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="5vNIVtZh41S" role="AHHXb">
                        <ref role="3cqZAo" node="5vNIVtZh40g" resolve="CertVerify_tail" />
                      </node>
                    </node>
                    <node concept="AH0OO" id="5vNIVtZh41T" role="37vLTJ">
                      <node concept="37vLTw" id="5vNIVtZh41U" role="AHEQo">
                        <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="5vNIVtZh41V" role="AHHXb">
                        <ref role="3cqZAo" node="5vNIVtZh41r" resolve="Decrypted_Merged_tail" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eOVzh" id="5vNIVtZh41W" role="3clFbw">
                <node concept="37vLTw" id="5vNIVtZh41X" role="3uHU7w">
                  <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
                </node>
                <node concept="3SuevK" id="5vNIVtZh41Y" role="3uHU7B">
                  <node concept="3qc1$W" id="5vNIVtZh41Z" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh420" role="3Sueug">
                    <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                  </node>
                </node>
              </node>
              <node concept="3eNFk2" id="5vNIVtZh421" role="3eNLev">
                <node concept="3eOVzh" id="5vNIVtZh422" role="3eO9$A">
                  <node concept="3cpWsd" id="5vNIVtZh423" role="3uHU7B">
                    <node concept="3SuevK" id="5vNIVtZh424" role="3uHU7B">
                      <node concept="3qc1$W" id="5vNIVtZh425" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="37vLTw" id="5vNIVtZh426" role="3Sueug">
                        <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="5vNIVtZh427" role="3uHU7w">
                      <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
                    </node>
                  </node>
                  <node concept="3SuevK" id="5vNIVtZh428" role="3uHU7w">
                    <node concept="3qc1$W" id="5vNIVtZh429" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="3cmrfG" id="5vNIVtZh42a" role="3Sueug">
                      <property role="3cmrfH" value="36" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbS" id="5vNIVtZh42b" role="3eOfB_">
                  <node concept="3clFbF" id="5vNIVtZh42c" role="3cqZAp">
                    <node concept="37vLTI" id="5vNIVtZh42d" role="3clFbG">
                      <node concept="AH0OO" id="5vNIVtZh42e" role="37vLTJ">
                        <node concept="37vLTw" id="5vNIVtZh42f" role="AHEQo">
                          <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="5vNIVtZh42g" role="AHHXb">
                          <ref role="3cqZAo" node="5vNIVtZh41r" resolve="Decrypted_Merged_tail" />
                        </node>
                      </node>
                      <node concept="SwV0n" id="5vNIVtZh42h" role="37vLTx">
                        <node concept="3cpWsd" id="5vNIVtZh42i" role="SwV0q">
                          <node concept="37vLTw" id="5vNIVtZh42j" role="3uHU7w">
                            <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
                          </node>
                          <node concept="3SuevK" id="5vNIVtZh42k" role="3uHU7B">
                            <node concept="3qc1$W" id="5vNIVtZh42l" role="3SuevR">
                              <property role="3qc1Xj" value="8" />
                            </node>
                            <node concept="37vLTw" id="5vNIVtZh42m" role="3Sueug">
                              <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="5vNIVtZh42n" role="SwV0s">
                          <ref role="3cqZAo" node="5vNIVtZh41$" resolve="ServFinRam" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eNFk2" id="5vNIVtZh42o" role="3eNLev">
                <node concept="3eOSWO" id="5vNIVtZh42p" role="3eO9$A">
                  <node concept="3cpWs3" id="5vNIVtZh42q" role="3uHU7w">
                    <node concept="3SuevK" id="5vNIVtZh42r" role="3uHU7w">
                      <node concept="3qc1$W" id="5vNIVtZh42s" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="5vNIVtZh42t" role="3Sueug">
                        <property role="3cmrfH" value="36" />
                      </node>
                    </node>
                    <node concept="37vLTw" id="5vNIVtZh42u" role="3uHU7B">
                      <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
                    </node>
                  </node>
                  <node concept="3SuevK" id="5vNIVtZh42v" role="3uHU7B">
                    <node concept="3qc1$W" id="5vNIVtZh42w" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="37vLTw" id="5vNIVtZh42x" role="3Sueug">
                      <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbS" id="5vNIVtZh42y" role="3eOfB_">
                  <node concept="3clFbF" id="5vNIVtZh42z" role="3cqZAp">
                    <node concept="37vLTI" id="5vNIVtZh42$" role="3clFbG">
                      <node concept="3SuevK" id="5vNIVtZh42_" role="37vLTx">
                        <node concept="3qc1$W" id="5vNIVtZh42A" role="3SuevR">
                          <property role="3qc1Xj" value="8" />
                        </node>
                        <node concept="3cmrfG" id="5vNIVtZh42B" role="3Sueug">
                          <property role="3cmrfH" value="0" />
                        </node>
                      </node>
                      <node concept="AH0OO" id="5vNIVtZh42C" role="37vLTJ">
                        <node concept="37vLTw" id="5vNIVtZh42D" role="AHEQo">
                          <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="5vNIVtZh42E" role="AHHXb">
                          <ref role="3cqZAo" node="5vNIVtZh41r" resolve="Decrypted_Merged_tail" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="5vNIVtZh42F" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="5vNIVtZh42G" role="1tU5fm" />
            <node concept="3cmrfG" id="5vNIVtZh42H" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="5vNIVtZh42I" role="1Dwp0S">
            <node concept="3cmrfG" id="5vNIVtZh42J" role="3uHU7w">
              <property role="3cmrfH" value="128" />
            </node>
            <node concept="37vLTw" id="5vNIVtZh42K" role="3uHU7B">
              <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="5vNIVtZh42L" role="1Dwrff">
            <node concept="37vLTw" id="5vNIVtZh42M" role="2$L3a6">
              <ref role="3cqZAo" node="5vNIVtZh42F" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh42N" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh42O" role="3cpWs9">
            <property role="TrG5h" value="Decrypted_Merged_tail_len" />
            <node concept="3qc1$W" id="5vNIVtZh42P" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3cpWs3" id="5vNIVtZh42Q" role="33vP2m">
              <node concept="3SuevK" id="5vNIVtZh42R" role="3uHU7w">
                <node concept="3qc1$W" id="5vNIVtZh42S" role="3SuevR">
                  <property role="3qc1Xj" value="8" />
                </node>
                <node concept="3cmrfG" id="5vNIVtZh42T" role="3Sueug">
                  <property role="3cmrfH" value="36" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh42U" role="3uHU7B">
                <ref role="3cqZAo" node="5vNIVtZh46g" resolve="CertVerify_tail_len" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh42V" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh42W" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh42X" role="3cpWs9">
            <property role="TrG5h" value="H7_H3" />
            <node concept="10Q1$e" id="5vNIVtZh42Y" role="1tU5fm">
              <node concept="10Q1$e" id="5vNIVtZh42Z" role="10Q1$1">
                <node concept="3qc1$W" id="5vNIVtZh430" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh431" role="33vP2m">
              <ref role="37wK5l" to="d2b1:7L_Qkl0gEu3" resolve="double_sha_from_checkpoint" />
              <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
              <node concept="37vLTw" id="5vNIVtZh432" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh46i" resolve="SHA_H_Checkpoint" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh433" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh466" resolve="TR3_len" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh434" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Zs" resolve="TR7_len" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh435" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh41r" resolve="Decrypted_Merged_tail" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh436" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh42O" resolve="Decrypted_Merged_tail_len" />
              </node>
              <node concept="3cpWsd" id="5vNIVtZh437" role="37wK5m">
                <node concept="3SuevK" id="5vNIVtZh438" role="3uHU7w">
                  <node concept="3qc1$W" id="5vNIVtZh439" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="3cmrfG" id="5vNIVtZh43a" role="3Sueug">
                    <property role="3cmrfH" value="36" />
                  </node>
                </node>
                <node concept="37vLTw" id="5vNIVtZh43b" role="3uHU7B">
                  <ref role="3cqZAo" node="5vNIVtZh42O" resolve="Decrypted_Merged_tail_len" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh43c" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh43d" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh43e" role="3cpWs9">
            <property role="TrG5h" value="H_7" />
            <node concept="10Q1$e" id="5vNIVtZh43f" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh43g" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="AH0OO" id="5vNIVtZh43h" role="33vP2m">
              <node concept="3cmrfG" id="5vNIVtZh43i" role="AHEQo">
                <property role="3cmrfH" value="0" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh43j" role="AHHXb">
                <ref role="3cqZAo" node="5vNIVtZh42X" resolve="H7_H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh43k" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh43l" role="3cpWs9">
            <property role="TrG5h" value="H_3" />
            <node concept="10Q1$e" id="5vNIVtZh43m" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh43n" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="AH0OO" id="5vNIVtZh43o" role="33vP2m">
              <node concept="3cmrfG" id="5vNIVtZh43p" role="AHEQo">
                <property role="3cmrfH" value="1" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh43q" role="AHHXb">
                <ref role="3cqZAo" node="5vNIVtZh42X" resolve="H7_H3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="5vNIVtZh43r" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="1Dw8fO" id="5vNIVtZh43s" role="8Wnug">
            <node concept="3clFbS" id="5vNIVtZh43t" role="2LFqv$">
              <node concept="vCCuG" id="5vNIVtZh43u" role="3cqZAp">
                <node concept="AH0OO" id="5vNIVtZh43v" role="vCCWX">
                  <node concept="37vLTw" id="5vNIVtZh43w" role="AHEQo">
                    <ref role="3cqZAo" node="5vNIVtZh43z" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh43x" role="AHHXb">
                    <ref role="3cqZAo" node="5vNIVtZh43l" resolve="H_3" />
                  </node>
                </node>
                <node concept="Xl_RD" id="5vNIVtZh43y" role="vCCx3">
                  <property role="Xl_RC" value="Hash from checkpoint" />
                </node>
              </node>
            </node>
            <node concept="3cpWsn" id="5vNIVtZh43z" role="1Duv9x">
              <property role="TrG5h" value="i" />
              <node concept="10Oyi0" id="5vNIVtZh43$" role="1tU5fm" />
              <node concept="3cmrfG" id="5vNIVtZh43_" role="33vP2m">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
            <node concept="3eOVzh" id="5vNIVtZh43A" role="1Dwp0S">
              <node concept="2OqwBi" id="5vNIVtZh43B" role="3uHU7w">
                <node concept="37vLTw" id="5vNIVtZh43C" role="2Oq$k0">
                  <ref role="3cqZAo" node="5vNIVtZh43l" resolve="H_3" />
                </node>
                <node concept="1Rwk04" id="5vNIVtZh43D" role="2OqNvi" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh43E" role="3uHU7B">
                <ref role="3cqZAo" node="5vNIVtZh43z" resolve="i" />
              </node>
            </node>
            <node concept="3uNrnE" id="5vNIVtZh43F" role="1Dwrff">
              <node concept="37vLTw" id="5vNIVtZh43G" role="2$L3a6">
                <ref role="3cqZAo" node="5vNIVtZh43z" resolve="i" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="5vNIVtZh43H" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3clFbH" id="5vNIVtZh43I" role="8Wnug" />
        </node>
        <node concept="3SKdUt" id="5vNIVtZh43J" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh43K" role="3SKWNk">
            <property role="3SKdUp" value="Derive the SF value from transcript hash H7 up to Certificate Verify" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh43L" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh43M" role="3cpWs9">
            <property role="TrG5h" value="fk_S" />
            <node concept="10Q1$e" id="5vNIVtZh43N" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh43O" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh43P" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh43Q" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh3Yy" resolve="SHTS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh43R" role="37wK5m">
                <property role="Xl_RC" value="finished" />
              </node>
              <node concept="2ShNRf" id="5vNIVtZh43S" role="37wK5m">
                <node concept="3$_iS1" id="5vNIVtZh43T" role="2ShVmc">
                  <node concept="3$GHV9" id="5vNIVtZh43U" role="3$GQph">
                    <node concept="3cmrfG" id="5vNIVtZh43V" role="3$I4v7">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3qc1$W" id="5vNIVtZh43W" role="3$_nBY">
                    <property role="3qc1Xj" value="8" />
                  </node>
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh43X" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh43Y" role="3cpWs9">
            <property role="TrG5h" value="SF_calculated" />
            <node concept="10Q1$e" id="5vNIVtZh43Z" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh440" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh441" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_14YZ" resolve="hmac" />
              <node concept="37vLTw" id="5vNIVtZh442" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh43M" resolve="fk_S" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh443" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh43e" resolve="H_7" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh444" role="3cqZAp" />
        <node concept="1X3_iC" id="5vNIVtZh445" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3SKdUt" id="5vNIVtZh446" role="8Wnug">
            <node concept="3SKdUq" id="5vNIVtZh447" role="3SKWNk">
              <property role="3SKdUp" value="Now, we need to calculate the actual SF value present in the transcript" />
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="5vNIVtZh448" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="3SKdUt" id="5vNIVtZh449" role="8Wnug">
            <node concept="3SKdUq" id="5vNIVtZh44a" role="3SKWNk">
              <property role="3SKdUp" value="We know that SF Verify Data is in the last 32 characters of ServerFinished (i+4 since header is 4 bytes long)" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh44b" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh44c" role="3cpWs9">
            <property role="TrG5h" value="SF_transcript" />
            <node concept="10Q1$e" id="5vNIVtZh44d" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh44e" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2ShNRf" id="5vNIVtZh44f" role="33vP2m">
              <node concept="3$_iS1" id="5vNIVtZh44g" role="2ShVmc">
                <node concept="3$GHV9" id="5vNIVtZh44h" role="3$GQph">
                  <node concept="3cmrfG" id="5vNIVtZh44i" role="3$I4v7">
                    <property role="3cmrfH" value="32" />
                  </node>
                </node>
                <node concept="3qc1$W" id="5vNIVtZh44j" role="3$_nBY">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh44k" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh44l" role="3cpWs9">
            <property role="TrG5h" value="ServerFinished_RAM" />
            <node concept="SEaj5" id="5vNIVtZh44m" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh44n" role="SEaiP">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="SEatS" id="5vNIVtZh44o" role="33vP2m">
              <node concept="3qc1$W" id="5vNIVtZh44p" role="6EdiW">
                <property role="3qc1Xj" value="8" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh44q" role="SEatU">
                <ref role="3cqZAo" node="5vNIVtZh412" resolve="ServerFinished" />
              </node>
            </node>
          </node>
        </node>
        <node concept="1Dw8fO" id="5vNIVtZh44r" role="3cqZAp">
          <node concept="3clFbS" id="5vNIVtZh44s" role="2LFqv$">
            <node concept="3clFbF" id="5vNIVtZh44t" role="3cqZAp">
              <node concept="37vLTI" id="5vNIVtZh44u" role="3clFbG">
                <node concept="SwV0n" id="5vNIVtZh44v" role="37vLTx">
                  <node concept="3cpWs3" id="5vNIVtZh44w" role="SwV0q">
                    <node concept="3SuevK" id="5vNIVtZh44x" role="3uHU7B">
                      <node concept="3qc1$W" id="5vNIVtZh44y" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="37vLTw" id="5vNIVtZh44z" role="3Sueug">
                        <ref role="3cqZAo" node="5vNIVtZh44F" resolve="i" />
                      </node>
                    </node>
                    <node concept="3SuevK" id="5vNIVtZh44$" role="3uHU7w">
                      <node concept="3qc1$W" id="5vNIVtZh44_" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="3cmrfG" id="5vNIVtZh44A" role="3Sueug">
                        <property role="3cmrfH" value="4" />
                      </node>
                    </node>
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh44B" role="SwV0s">
                    <ref role="3cqZAo" node="5vNIVtZh44l" resolve="ServerFinished_RAM" />
                  </node>
                </node>
                <node concept="AH0OO" id="5vNIVtZh44C" role="37vLTJ">
                  <node concept="37vLTw" id="5vNIVtZh44D" role="AHEQo">
                    <ref role="3cqZAo" node="5vNIVtZh44F" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="5vNIVtZh44E" role="AHHXb">
                    <ref role="3cqZAo" node="5vNIVtZh44c" resolve="SF_transcript" />
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="5vNIVtZh44F" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="5vNIVtZh44G" role="1tU5fm" />
            <node concept="3cmrfG" id="5vNIVtZh44H" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="5vNIVtZh44I" role="1Dwp0S">
            <node concept="37vLTw" id="5vNIVtZh44J" role="3uHU7B">
              <ref role="3cqZAo" node="5vNIVtZh44F" resolve="i" />
            </node>
            <node concept="3cmrfG" id="5vNIVtZh44K" role="3uHU7w">
              <property role="3cmrfH" value="32" />
            </node>
          </node>
          <node concept="3uNrnE" id="5vNIVtZh44L" role="1Dwrff">
            <node concept="37vLTw" id="5vNIVtZh44M" role="2$L3a6">
              <ref role="3cqZAo" node="5vNIVtZh44F" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh44N" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh44O" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh44P" role="3SKWNk">
            <property role="3SKdUp" value="Verify that the two SF values are identical" />
          </node>
        </node>
        <node concept="3s6pcg" id="5vNIVtZh44Q" role="3cqZAp">
          <node concept="2YIFZM" id="5vNIVtZh44R" role="3s6pch">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <node concept="37vLTw" id="5vNIVtZh44S" role="37wK5m">
              <ref role="3cqZAo" node="5vNIVtZh43Y" resolve="SF_calculated" />
            </node>
          </node>
          <node concept="2YIFZM" id="5vNIVtZh44T" role="3s6pci">
            <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
            <ref role="37wK5l" to="d2b1:2OJAT4DNwgk" resolve="combine_8_into_256" />
            <node concept="37vLTw" id="5vNIVtZh44U" role="37wK5m">
              <ref role="3cqZAo" node="5vNIVtZh44c" resolve="SF_transcript" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh44V" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh44W" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh44X" role="3cpWs9">
            <property role="TrG5h" value="dHS" />
            <node concept="10Q1$e" id="5vNIVtZh44Y" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh44Z" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh450" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh451" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh460" resolve="HS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh452" role="37wK5m">
                <property role="Xl_RC" value="derived" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh453" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$NbfY" resolve="SHA2" />
                <ref role="37wK5l" to="d2b1:2OJAT4_1dPi" resolve="hash_of_empty" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh454" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh455" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh456" role="3cpWs9">
            <property role="TrG5h" value="MS" />
            <node concept="10Q1$e" id="5vNIVtZh457" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh458" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh459" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_15q9" resolve="hkdf_extract" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh45a" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh44X" resolve="dHS" />
              </node>
              <node concept="2YIFZM" id="5vNIVtZh45b" role="37wK5m">
                <ref role="1Pybhc" to="d2b1:2OJAT4$Ngvf" resolve="Util" />
                <ref role="37wK5l" to="d2b1:2OJAT4_03eA" resolve="new_zero_array" />
                <node concept="3cmrfG" id="5vNIVtZh45c" role="37wK5m">
                  <property role="3cmrfH" value="32" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh45d" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh45e" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh45f" role="3cpWs9">
            <property role="TrG5h" value="CATS" />
            <node concept="10Q1$e" id="5vNIVtZh45g" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh45h" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh45i" role="33vP2m">
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <ref role="37wK5l" node="2OJAT4_16Ah" resolve="hkdf_expand_derive_secret" />
              <node concept="37vLTw" id="5vNIVtZh45j" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh456" resolve="MS" />
              </node>
              <node concept="Xl_RD" id="5vNIVtZh45k" role="37wK5m">
                <property role="Xl_RC" value="c ap traffic" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45l" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh43l" resolve="H_3" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh45m" role="3cqZAp" />
        <node concept="3SKdUt" id="5vNIVtZh45n" role="3cqZAp">
          <node concept="3SKdUq" id="5vNIVtZh45o" role="3SKWNk">
            <property role="3SKdUp" value="client application traffic key, iv" />
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh45p" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh45q" role="3cpWs9">
            <property role="TrG5h" value="tk_capp" />
            <node concept="10Q1$e" id="5vNIVtZh45r" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh45s" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh45t" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_166j" resolve="hkdf_expand_derive_tk" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh45u" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh45f" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh45v" role="37wK5m">
                <property role="3cmrfH" value="16" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5vNIVtZh45w" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh45x" role="3cpWs9">
            <property role="TrG5h" value="iv_capp" />
            <node concept="10Q1$e" id="5vNIVtZh45y" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh45z" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh45$" role="33vP2m">
              <ref role="37wK5l" node="2OJAT4_16mi" resolve="hkdf_expand_derive_iv" />
              <ref role="1Pybhc" node="2OJAT4$Z6ed" resolve="HKDF" />
              <node concept="37vLTw" id="5vNIVtZh45_" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh45f" resolve="CATS" />
              </node>
              <node concept="3cmrfG" id="5vNIVtZh45A" role="37wK5m">
                <property role="3cmrfH" value="12" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh45B" role="3cqZAp" />
        <node concept="3cpWs8" id="5vNIVtZh45C" role="3cqZAp">
          <node concept="3cpWsn" id="5vNIVtZh45D" role="3cpWs9">
            <property role="TrG5h" value="dns_plaintext" />
            <node concept="10Q1$e" id="5vNIVtZh45E" role="1tU5fm">
              <node concept="3qc1$W" id="5vNIVtZh45F" role="10Q1$1">
                <property role="3qc1Xj" value="8" />
              </node>
            </node>
            <node concept="2YIFZM" id="5vNIVtZh45G" role="33vP2m">
              <ref role="37wK5l" to="liel:2OJAT4DzYl6" resolve="aes_gcm_decrypt" />
              <ref role="1Pybhc" to="liel:2OJAT4_dWEz" resolve="AES_GCM" />
              <node concept="37vLTw" id="5vNIVtZh45H" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh45q" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45I" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh45x" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45J" role="37wK5m">
                <ref role="3cqZAo" node="5vNIVtZh46l" resolve="appl_ct" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5vNIVtZh45K" role="3cqZAp" />
        <node concept="3cpWs6" id="5vNIVtZh45L" role="3cqZAp">
          <node concept="2ShNRf" id="5vNIVtZh45M" role="3cqZAk">
            <node concept="3g6Rrh" id="5vNIVtZh45N" role="2ShVmc">
              <node concept="10Q1$e" id="5vNIVtZh45O" role="3g7fb8">
                <node concept="3qc1$W" id="5vNIVtZh45P" role="10Q1$1">
                  <property role="3qc1Xj" value="8" />
                </node>
              </node>
              <node concept="37vLTw" id="5vNIVtZh45Q" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh45D" resolve="dns_plaintext" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45R" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh3YG" resolve="tk_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45S" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh3YN" resolve="iv_shs" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45T" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh45q" resolve="tk_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45U" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh45x" resolve="iv_capp" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45V" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh43l" resolve="H_3" />
              </node>
              <node concept="37vLTw" id="5vNIVtZh45W" role="3g7hyw">
                <ref role="3cqZAo" node="5vNIVtZh43Y" resolve="SF_calculated" />
              </node>
            </node>
          </node>
        </node>
      </node>
      <node concept="10Q1$e" id="5vNIVtZh45X" role="3clF45">
        <node concept="10Q1$e" id="5vNIVtZh45Y" role="10Q1$1">
          <node concept="3qc1$W" id="5vNIVtZh45Z" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh460" role="3clF46">
        <property role="TrG5h" value="HS" />
        <node concept="10Q1$e" id="5vNIVtZh461" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh462" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh463" role="3clF46">
        <property role="TrG5h" value="H2" />
        <node concept="10Q1$e" id="5vNIVtZh464" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh465" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh466" role="3clF46">
        <property role="TrG5h" value="TR3_len" />
        <node concept="3qc1$W" id="5vNIVtZh467" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh468" role="3clF46">
        <property role="TrG5h" value="CertVerify_len" />
        <node concept="3qc1$W" id="5vNIVtZh469" role="1tU5fm">
          <property role="3qc1Xj" value="16" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh46a" role="3clF46">
        <property role="TrG5h" value="CertVerify_ct_tail" />
        <node concept="10Q1$e" id="5vNIVtZh46b" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh46c" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh46d" role="3clF46">
        <property role="TrG5h" value="ServerFinished_ct" />
        <node concept="10Q1$e" id="5vNIVtZh46e" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh46f" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh46g" role="3clF46">
        <property role="TrG5h" value="CertVerify_tail_len" />
        <node concept="3qc1$W" id="5vNIVtZh46h" role="1tU5fm">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh46i" role="3clF46">
        <property role="TrG5h" value="SHA_H_Checkpoint" />
        <node concept="10Q1$e" id="5vNIVtZh46j" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh46k" role="10Q1$1">
            <property role="3qc1Xj" value="32" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5vNIVtZh46l" role="3clF46">
        <property role="TrG5h" value="appl_ct" />
        <node concept="10Q1$e" id="5vNIVtZh46m" role="1tU5fm">
          <node concept="3qc1$W" id="5vNIVtZh46n" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5vNIVtZh46o" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="5vNIVtZh46p" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46q" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46r" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46s" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46t" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46u" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46v" role="jymVt" />
    <node concept="2tJIrI" id="5vNIVtZh46w" role="jymVt" />
  </node>
</model>

