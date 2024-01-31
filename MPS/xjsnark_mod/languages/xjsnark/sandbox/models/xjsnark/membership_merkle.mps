<?xml version="1.0" encoding="UTF-8"?>
<model ref="r:e9f46738-b295-4cc2-81b5-488d79fb4d01(xjsnark.membership_merkle)">
  <persistence version="9" />
  <languages>
    <use id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage" version="4" />
    <use id="0688d542-e2a3-492c-a31f-0e921fd6a8fb" name="xjsnark" version="0" />
  </languages>
  <imports>
    <import index="tluv" ref="r:497ff602-8d96-4239-8b0f-254445ada898(xjsnark.field_table)" />
    <import index="7dh8" ref="r:09123713-f163-4703-a727-7cf154b91a1d(xjsnark.poseidon)" />
    <import index="w19y" ref="r:e9f46738-b295-4cc2-81b5-488d79fb4d01(xjsnark.membership_merkle)" />
    <import index="jgwk" ref="r:53cbaa09-60e6-4e11-b6d1-50c0fc36288c(xjsnark.PolicyCheck)" />
  </imports>
  <registry>
    <language id="f3061a53-9226-4cc5-a443-f952ceaf5816" name="jetbrains.mps.baseLanguage">
      <concept id="1082485599095" name="jetbrains.mps.baseLanguage.structure.BlockStatement" flags="nn" index="9aQIb">
        <child id="1082485599096" name="statements" index="9aQI4" />
      </concept>
      <concept id="1215693861676" name="jetbrains.mps.baseLanguage.structure.BaseAssignmentExpression" flags="nn" index="d038R">
        <child id="1068498886297" name="rValue" index="37vLTx" />
        <child id="1068498886295" name="lValue" index="37vLTJ" />
      </concept>
      <concept id="1153417849900" name="jetbrains.mps.baseLanguage.structure.GreaterThanOrEqualsExpression" flags="nn" index="2d3UOw" />
      <concept id="1202948039474" name="jetbrains.mps.baseLanguage.structure.InstanceMethodCallOperation" flags="nn" index="liA8E" />
      <concept id="1179360813171" name="jetbrains.mps.baseLanguage.structure.HexIntegerLiteral" flags="nn" index="2nou5x">
        <property id="1179360856892" name="value" index="2noCCI" />
      </concept>
      <concept id="1465982738277781862" name="jetbrains.mps.baseLanguage.structure.PlaceholderMember" flags="ng" index="2tJIrI" />
      <concept id="1239714755177" name="jetbrains.mps.baseLanguage.structure.AbstractUnaryNumberOperation" flags="nn" index="2$Kvd9">
        <child id="1239714902950" name="expression" index="2$L3a6" />
      </concept>
      <concept id="1173175405605" name="jetbrains.mps.baseLanguage.structure.ArrayAccessExpression" flags="nn" index="AH0OO">
        <child id="1173175577737" name="index" index="AHEQo" />
        <child id="1173175590490" name="array" index="AHHXb" />
      </concept>
      <concept id="1154032098014" name="jetbrains.mps.baseLanguage.structure.AbstractLoopStatement" flags="nn" index="2LF5Ji">
        <child id="1154032183016" name="body" index="2LFqv$" />
      </concept>
      <concept id="1197027756228" name="jetbrains.mps.baseLanguage.structure.DotExpression" flags="nn" index="2OqwBi">
        <child id="1197027771414" name="operand" index="2Oq$k0" />
        <child id="1197027833540" name="operation" index="2OqNvi" />
      </concept>
      <concept id="1197029447546" name="jetbrains.mps.baseLanguage.structure.FieldReferenceOperation" flags="nn" index="2OwXpG">
        <reference id="1197029500499" name="fieldDeclaration" index="2Oxat5" />
      </concept>
      <concept id="1145552977093" name="jetbrains.mps.baseLanguage.structure.GenericNewExpression" flags="nn" index="2ShNRf">
        <child id="1145553007750" name="creator" index="2ShVmc" />
      </concept>
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
      <concept id="1070533707846" name="jetbrains.mps.baseLanguage.structure.StaticFieldReference" flags="nn" index="10M0yZ">
        <reference id="1144433057691" name="classifier" index="1PxDUh" />
      </concept>
      <concept id="1070534370425" name="jetbrains.mps.baseLanguage.structure.IntegerType" flags="in" index="10Oyi0" />
      <concept id="1070534760951" name="jetbrains.mps.baseLanguage.structure.ArrayType" flags="in" index="10Q1$e">
        <child id="1070534760952" name="componentType" index="10Q1$1" />
      </concept>
      <concept id="1068390468200" name="jetbrains.mps.baseLanguage.structure.FieldDeclaration" flags="ig" index="312cEg">
        <property id="8606350594693632173" name="isTransient" index="eg7rD" />
        <property id="1240249534625" name="isVolatile" index="34CwA1" />
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
      <concept id="1068580123165" name="jetbrains.mps.baseLanguage.structure.InstanceMethodDeclaration" flags="ig" index="3clFb_">
        <property id="1178608670077" name="isAbstract" index="1EzhhJ" />
      </concept>
      <concept id="1068580123155" name="jetbrains.mps.baseLanguage.structure.ExpressionStatement" flags="nn" index="3clFbF">
        <child id="1068580123156" name="expression" index="3clFbG" />
      </concept>
      <concept id="1068580123157" name="jetbrains.mps.baseLanguage.structure.Statement" flags="nn" index="3clFbH" />
      <concept id="1068580123159" name="jetbrains.mps.baseLanguage.structure.IfStatement" flags="nn" index="3clFbJ">
        <child id="1082485599094" name="ifFalseStatement" index="9aQIa" />
        <child id="1068580123160" name="condition" index="3clFbw" />
        <child id="1068580123161" name="ifTrue" index="3clFbx" />
        <child id="1206060520071" name="elsifClauses" index="3eNLev" />
      </concept>
      <concept id="1068580123136" name="jetbrains.mps.baseLanguage.structure.StatementList" flags="sn" stub="5293379017992965193" index="3clFbS">
        <child id="1068581517665" name="statement" index="3cqZAp" />
      </concept>
      <concept id="1068580123140" name="jetbrains.mps.baseLanguage.structure.ConstructorDeclaration" flags="ig" index="3clFbW" />
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
      <concept id="1068581242863" name="jetbrains.mps.baseLanguage.structure.LocalVariableDeclaration" flags="nr" index="3cpWsn" />
      <concept id="1068581517677" name="jetbrains.mps.baseLanguage.structure.VoidType" flags="in" index="3cqZAl" />
      <concept id="1206060495898" name="jetbrains.mps.baseLanguage.structure.ElsifClause" flags="ng" index="3eNFk2">
        <child id="1206060619838" name="condition" index="3eO9$A" />
        <child id="1206060644605" name="statementList" index="3eOfB_" />
      </concept>
      <concept id="1079359253375" name="jetbrains.mps.baseLanguage.structure.ParenthesizedExpression" flags="nn" index="1eOMI4">
        <child id="1079359253376" name="expression" index="1eOMHV" />
      </concept>
      <concept id="1081506773034" name="jetbrains.mps.baseLanguage.structure.LessThanExpression" flags="nn" index="3eOVzh" />
      <concept id="1204053956946" name="jetbrains.mps.baseLanguage.structure.IMethodCall" flags="ng" index="1ndlxa">
        <reference id="1068499141037" name="baseMethodDeclaration" index="37wK5l" />
        <child id="1068499141038" name="actualArgument" index="37wK5m" />
      </concept>
      <concept id="1212685548494" name="jetbrains.mps.baseLanguage.structure.ClassCreator" flags="nn" index="1pGfFk" />
      <concept id="1107461130800" name="jetbrains.mps.baseLanguage.structure.Classifier" flags="ng" index="3pOWGL">
        <child id="5375687026011219971" name="member" index="jymVt" unordered="true" />
      </concept>
      <concept id="7812454656619025416" name="jetbrains.mps.baseLanguage.structure.MethodDeclaration" flags="ng" index="1rXfSm">
        <property id="8355037393041754995" name="isNative" index="2aFKle" />
      </concept>
      <concept id="7812454656619025412" name="jetbrains.mps.baseLanguage.structure.LocalMethodCall" flags="nn" index="1rXfSq" />
      <concept id="1107535904670" name="jetbrains.mps.baseLanguage.structure.ClassifierType" flags="in" index="3uibUv">
        <reference id="1107535924139" name="classifier" index="3uigEE" />
      </concept>
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
      <concept id="1225892208569" name="jetbrains.mps.baseLanguage.structure.ShiftLeftExpression" flags="nn" index="1GRDU$" />
      <concept id="1163668896201" name="jetbrains.mps.baseLanguage.structure.TernaryOperatorExpression" flags="nn" index="3K4zz7">
        <child id="1163668914799" name="condition" index="3K4Cdx" />
        <child id="1163668922816" name="ifTrue" index="3K4E3e" />
        <child id="1163668934364" name="ifFalse" index="3K4GZi" />
      </concept>
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
      <concept id="7495353643810622554" name="xjsnark.structure.JFieldConversion" flags="ng" index="_hXgR">
        <child id="7495353643810622556" name="argument" index="_hXgL" />
        <child id="7495353643810622555" name="jType" index="_hXgQ" />
      </concept>
      <concept id="5462301061293008935" name="xjsnark.structure.JEqualsExpression" flags="ng" index="2_lxnS" />
      <concept id="7553992366106506034" name="xjsnark.structure.JFieldType" flags="ig" index="2D7PWU">
        <reference id="7553992366106506060" name="fieldRec" index="2D7PX4" />
      </concept>
      <concept id="7495353643616961541" name="xjsnark.structure.SingleLineCommentClassMember" flags="ng" index="DJdLC">
        <property id="7495353643619293787" name="text" index="DRO8Q" />
      </concept>
      <concept id="7553992366094736353" name="xjsnark.structure.VerifyStatement" flags="ng" index="2DKZvD">
        <child id="7553992366094744703" name="condition" index="2DKX1R" />
      </concept>
      <concept id="8078876767577527548" name="xjsnark.structure.StructDefinition" flags="ig" index="2VwbHx" />
      <concept id="4165393367774947854" name="xjsnark.structure.JUnsignedIntegerType" flags="ig" index="3qc1$W">
        <property id="4165393367774948449" name="bitwidth" index="3qc1Xj" />
      </concept>
      <concept id="8340315132972716924" name="xjsnark.structure.VerifyEqStatement" flags="ng" index="3s6pcg">
        <child id="8340315132972716925" name="exp1" index="3s6pch" />
        <child id="8340315132972716926" name="exp2" index="3s6pci" />
      </concept>
      <concept id="6307611378306596055" name="xjsnark.structure.JBooleanType" flags="ig" index="1QD1ZQ" />
      <concept id="9096502420330357553" name="xjsnark.structure.JUnsignedIntegerConversion" flags="ng" index="3SuevK">
        <child id="9096502420330357585" name="argument" index="3Sueug" />
        <child id="9096502420330357558" name="jType" index="3SuevR" />
      </concept>
      <concept id="6238098797407520605" name="xjsnark.structure.BitsOp" flags="ng" index="1VPAEj" />
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
  <node concept="2VwbHx" id="3FDiKWYz72y">
    <property role="TrG5h" value="MerkleAuthPath" />
    <node concept="DJdLC" id="3h4liP5bSpi" role="jymVt">
      <property role="DRO8Q" value="directionSelector chooses the direction of Merkle Path (left or right concatenation)" />
    </node>
    <node concept="DJdLC" id="3h4liP5bX07" role="jymVt">
      <property role="DRO8Q" value="directionSelector also happens to be the index of leaf in the first layer" />
    </node>
    <node concept="312cEg" id="3FDiKWYzc48" role="jymVt">
      <property role="34CwA1" value="false" />
      <property role="eg7rD" value="false" />
      <property role="TrG5h" value="directionSelector" />
      <property role="3TUv4t" value="false" />
      <node concept="3qc1$W" id="3FDiKWYzc44" role="1tU5fm">
        <property role="3qc1Xj" value="64" />
      </node>
      <node concept="3Tm1VV" id="LEx6Grj4pa" role="1B3o_S" />
    </node>
    <node concept="DJdLC" id="3h4liP5bSG4" role="jymVt">
      <property role="DRO8Q" value="digests stores all the hashes in a Merkle Path" />
    </node>
    <node concept="312cEg" id="6GTEY6fYmeS" role="jymVt">
      <property role="34CwA1" value="false" />
      <property role="eg7rD" value="false" />
      <property role="TrG5h" value="digests" />
      <property role="3TUv4t" value="false" />
      <node concept="10Q1$e" id="6GTEY6fYmeL" role="1tU5fm">
        <node concept="2D7PWU" id="6GTEY6fYm62" role="10Q1$1">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="3Tm1VV" id="LEx6Grj4hH" role="1B3o_S" />
      <node concept="2ShNRf" id="LEx6GrZ99Z" role="33vP2m">
        <node concept="3$_iS1" id="LEx6GrZ9a0" role="2ShVmc">
          <node concept="3$GHV9" id="LEx6GrZ9a1" role="3$GQph">
            <node concept="10M0yZ" id="LEx6GrZ9a2" role="3$I4v7">
              <ref role="1PxDUh" node="2NUrvUr11zd" resolve="membership_proof_functions" />
              <ref role="3cqZAo" node="2NUrvUuvOBt" resolve="HEIGHT" />
            </node>
          </node>
          <node concept="2D7PWU" id="LEx6GrZ9a3" role="3$_nBY">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="3FDiKWYzcqo" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5bSZd" role="jymVt">
      <property role="DRO8Q" value="initialization" />
    </node>
    <node concept="3clFbW" id="3FDiKWYzcmh" role="jymVt">
      <node concept="3cqZAl" id="3FDiKWYzcmi" role="3clF45" />
      <node concept="3clFbS" id="3FDiKWYzcmj" role="3clF47">
        <node concept="3clFbF" id="6GTEY6hE6sR" role="3cqZAp">
          <node concept="37vLTI" id="6GTEY6hE6tM" role="3clFbG">
            <node concept="2ShNRf" id="6GTEY6hE6uN" role="37vLTx">
              <node concept="3$_iS1" id="6GTEY6hE6xW" role="2ShVmc">
                <node concept="3$GHV9" id="6GTEY6hE6xY" role="3$GQph">
                  <node concept="10M0yZ" id="2NUrvUuvPlm" role="3$I4v7">
                    <ref role="1PxDUh" node="2NUrvUr11zd" resolve="membership_proof_functions" />
                    <ref role="3cqZAo" node="2NUrvUuvOBt" resolve="HEIGHT" />
                  </node>
                </node>
                <node concept="2D7PWU" id="6GTEY6hE6xV" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
            <node concept="37vLTw" id="6GTEY6jMPp$" role="37vLTJ">
              <ref role="3cqZAo" node="6GTEY6fYmeS" resolve="digests" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="3FDiKWYzcmB" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="3h4liP5bThG" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5bT8o" role="jymVt">
      <property role="DRO8Q" value="initialization" />
    </node>
    <node concept="3clFbW" id="5MGqnSkqBqm" role="jymVt">
      <node concept="3cqZAl" id="5MGqnSkqBqn" role="3clF45" />
      <node concept="3clFbS" id="5MGqnSkqBqo" role="3clF47">
        <node concept="3clFbF" id="5MGqnSkqBqp" role="3cqZAp">
          <node concept="37vLTI" id="5MGqnSkqBqq" role="3clFbG">
            <node concept="37vLTw" id="5MGqnSkqBqr" role="37vLTx">
              <ref role="3cqZAo" node="5MGqnSkqBqy" resolve="inputDS" />
            </node>
            <node concept="37vLTw" id="5MGqnSkqBqs" role="37vLTJ">
              <ref role="3cqZAo" node="3FDiKWYzc48" resolve="directionSelector" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="5MGqnSkqBqt" role="3cqZAp">
          <node concept="37vLTI" id="5MGqnSkqBqu" role="3clFbG">
            <node concept="37vLTw" id="5MGqnSkqBqv" role="37vLTx">
              <ref role="3cqZAo" node="5MGqnSkqBq$" resolve="inputDigests" />
            </node>
            <node concept="37vLTw" id="5MGqnSkqBqw" role="37vLTJ">
              <ref role="3cqZAo" node="6GTEY6fYmeS" resolve="digests" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="5MGqnSkqBqx" role="1B3o_S" />
      <node concept="37vLTG" id="5MGqnSkqBqy" role="3clF46">
        <property role="TrG5h" value="inputDS" />
        <node concept="3qc1$W" id="5MGqnSkqBqz" role="1tU5fm">
          <property role="3qc1Xj" value="64" />
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBq$" role="3clF46">
        <property role="TrG5h" value="inputDigests" />
        <node concept="10Q1$e" id="5MGqnSkqBq_" role="1tU5fm">
          <node concept="2D7PWU" id="5MGqnSkqBqA" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="2NUrvUw_kHB" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5bT$k" role="jymVt">
      <property role="DRO8Q" value="Compute Mekle Root in a verifiable manner using left, path and direction as input" />
    </node>
    <node concept="DJdLC" id="3h4liP5bU0E" role="jymVt">
      <property role="DRO8Q" value="Return the computed Merkle Root" />
    </node>
    <node concept="3clFb_" id="3FDiKWYzdx6" role="jymVt">
      <property role="1EzhhJ" value="false" />
      <property role="TrG5h" value="computeMerkleRoot" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="3FDiKWYzdx9" role="3clF47">
        <node concept="3cpWs8" id="3FDiKWYzdCg" role="3cqZAp">
          <node concept="3cpWsn" id="3FDiKWYzdCj" role="3cpWs9">
            <property role="TrG5h" value="directionBits" />
            <node concept="10Q1$e" id="3FDiKWYzdCs" role="1tU5fm">
              <node concept="1QD1ZQ" id="3FDiKWYzdCf" role="10Q1$1" />
            </node>
            <node concept="2OqwBi" id="3FDiKWYzdEO" role="33vP2m">
              <node concept="37vLTw" id="3FDiKWYzdE1" role="2Oq$k0">
                <ref role="3cqZAo" node="3FDiKWYzc48" resolve="directionSelector" />
              </node>
              <node concept="1VPAEj" id="3FDiKWYzdFH" role="2OqNvi" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="3FDiKWYzdH4" role="3cqZAp">
          <node concept="3cpWsn" id="3FDiKWYzdH7" role="3cpWs9">
            <property role="TrG5h" value="currentDigest" />
            <node concept="2D7PWU" id="3FDiKWYzdH2" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="37vLTw" id="3FDiKWYzdIE" role="33vP2m">
              <ref role="3cqZAo" node="3FDiKWYzd$S" resolve="leaf" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="3FDiKWYzdOE" role="3cqZAp">
          <node concept="3cpWsn" id="3FDiKWYzdOH" role="3cpWs9">
            <property role="TrG5h" value="inputToNextHash" />
            <node concept="10Q1$e" id="3FDiKWYzfJy" role="1tU5fm">
              <node concept="2D7PWU" id="3FDiKWYzdOC" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="2ShNRf" id="3FDiKWYzfTl" role="33vP2m">
              <node concept="3$_iS1" id="3FDiKWYzg1h" role="2ShVmc">
                <node concept="3$GHV9" id="3FDiKWYzg1j" role="3$GQph">
                  <node concept="3cmrfG" id="3FDiKWYzg6t" role="3$I4v7">
                    <property role="3cmrfH" value="2" />
                  </node>
                </node>
                <node concept="2D7PWU" id="3FDiKWYzg1g" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="3FDiKWYzdQq" role="3cqZAp" />
        <node concept="1Dw8fO" id="3FDiKWYzdS9" role="3cqZAp">
          <node concept="3clFbS" id="3FDiKWYzdSb" role="2LFqv$">
            <node concept="1Dw8fO" id="3FDiKWYzehP" role="3cqZAp">
              <node concept="3clFbS" id="3FDiKWYzehR" role="2LFqv$">
                <node concept="3clFbJ" id="3FDiKWYzecK" role="3cqZAp">
                  <node concept="3clFbS" id="3FDiKWYzecM" role="3clFbx">
                    <node concept="3clFbF" id="3FDiKWYzeg6" role="3cqZAp">
                      <node concept="37vLTI" id="3FDiKWYzegQ" role="3clFbG">
                        <node concept="3K4zz7" id="3FDiKWYzeNv" role="37vLTx">
                          <node concept="37vLTw" id="3FDiKWYzeQ4" role="3K4E3e">
                            <ref role="3cqZAo" node="3FDiKWYzdH7" resolve="currentDigest" />
                          </node>
                          <node concept="AH0OO" id="3FDiKWYzeVY" role="3K4GZi">
                            <node concept="37vLTw" id="3FDiKWYzeZb" role="AHEQo">
                              <ref role="3cqZAo" node="3FDiKWYzdSc" resolve="i" />
                            </node>
                            <node concept="37vLTw" id="6GTEY6jvLsV" role="AHHXb">
                              <ref role="3cqZAo" node="6GTEY6fYmeS" resolve="digests" />
                            </node>
                          </node>
                          <node concept="2d3UOw" id="3FDiKWYzeGA" role="3K4Cdx">
                            <node concept="3cmrfG" id="3FDiKWYzeH3" role="3uHU7w">
                              <property role="3cmrfH" value="1" />
                            </node>
                            <node concept="37vLTw" id="3FDiKWYzeBh" role="3uHU7B">
                              <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                            </node>
                          </node>
                        </node>
                        <node concept="AH0OO" id="3FDiKWYzgbP" role="37vLTJ">
                          <node concept="37vLTw" id="3FDiKWYzgfn" role="AHEQo">
                            <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                          </node>
                          <node concept="37vLTw" id="3FDiKWYzeg4" role="AHHXb">
                            <ref role="3cqZAo" node="3FDiKWYzdOH" resolve="inputToNextHash" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="AH0OO" id="3FDiKWYzedV" role="3clFbw">
                    <node concept="37vLTw" id="3FDiKWYzeeF" role="AHEQo">
                      <ref role="3cqZAo" node="3FDiKWYzdSc" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="3FDiKWYzed9" role="AHHXb">
                      <ref role="3cqZAo" node="3FDiKWYzdCj" resolve="directionBits" />
                    </node>
                  </node>
                  <node concept="9aQIb" id="3FDiKWYzf8_" role="9aQIa">
                    <node concept="3clFbS" id="3FDiKWYzf8A" role="9aQI4">
                      <node concept="3clFbF" id="3FDiKWYzfbX" role="3cqZAp">
                        <node concept="37vLTI" id="3FDiKWYzfbY" role="3clFbG">
                          <node concept="3K4zz7" id="3FDiKWYzfbZ" role="37vLTx">
                            <node concept="37vLTw" id="3FDiKWYzfc0" role="3K4E3e">
                              <ref role="3cqZAo" node="3FDiKWYzdH7" resolve="currentDigest" />
                            </node>
                            <node concept="AH0OO" id="3FDiKWYzfc1" role="3K4GZi">
                              <node concept="37vLTw" id="3FDiKWYzfc2" role="AHEQo">
                                <ref role="3cqZAo" node="3FDiKWYzdSc" resolve="i" />
                              </node>
                              <node concept="37vLTw" id="6GTEY6jvLzo" role="AHHXb">
                                <ref role="3cqZAo" node="6GTEY6fYmeS" resolve="digests" />
                              </node>
                            </node>
                            <node concept="3eOVzh" id="3FDiKWYzffk" role="3K4Cdx">
                              <node concept="37vLTw" id="3FDiKWYzfc6" role="3uHU7B">
                                <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                              </node>
                              <node concept="3cmrfG" id="3FDiKWYzfc5" role="3uHU7w">
                                <property role="3cmrfH" value="1" />
                              </node>
                            </node>
                          </node>
                          <node concept="AH0OO" id="3FDiKWYzgiY" role="37vLTJ">
                            <node concept="37vLTw" id="3FDiKWYzgms" role="AHEQo">
                              <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                            </node>
                            <node concept="37vLTw" id="3FDiKWYzfc7" role="AHHXb">
                              <ref role="3cqZAo" node="3FDiKWYzdOH" resolve="inputToNextHash" />
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3cpWsn" id="3FDiKWYzehS" role="1Duv9x">
                <property role="TrG5h" value="j" />
                <node concept="10Oyi0" id="3FDiKWYzei9" role="1tU5fm" />
                <node concept="3cmrfG" id="3FDiKWYzei$" role="33vP2m">
                  <property role="3cmrfH" value="0" />
                </node>
              </node>
              <node concept="3eOVzh" id="3FDiKWYzeoG" role="1Dwp0S">
                <node concept="37vLTw" id="3FDiKWYzejq" role="3uHU7B">
                  <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                </node>
                <node concept="3cmrfG" id="3FDiKWYzerm" role="3uHU7w">
                  <property role="3cmrfH" value="2" />
                </node>
              </node>
              <node concept="3uNrnE" id="3FDiKWYzezA" role="1Dwrff">
                <node concept="37vLTw" id="3FDiKWYzezC" role="2$L3a6">
                  <ref role="3cqZAo" node="3FDiKWYzehS" resolve="j" />
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="6QM7J$VD0Ot" role="3cqZAp">
              <node concept="37vLTI" id="6QM7J$VD0UZ" role="3clFbG">
                <node concept="2YIFZM" id="6QM7J$VD19_" role="37vLTx">
                  <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
                  <ref role="37wK5l" to="7dh8:LEx6GtB4G1" resolve="poseidon_hash" />
                  <node concept="37vLTw" id="6QM7J$VD1a5" role="37wK5m">
                    <ref role="3cqZAo" node="3FDiKWYzdOH" resolve="inputToNextHash" />
                  </node>
                </node>
                <node concept="37vLTw" id="6QM7J$VD0Or" role="37vLTJ">
                  <ref role="3cqZAo" node="3FDiKWYzdH7" resolve="currentDigest" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="3FDiKWYzdSc" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="3FDiKWYzdT1" role="1tU5fm" />
            <node concept="3cmrfG" id="3FDiKWYzdTy" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="3FDiKWYzdZ3" role="1Dwp0S">
            <node concept="37vLTw" id="3FDiKWYzdTR" role="3uHU7B">
              <ref role="3cqZAo" node="3FDiKWYzdSc" resolve="i" />
            </node>
            <node concept="10M0yZ" id="5OGfKn$8CJD" role="3uHU7w">
              <ref role="1PxDUh" node="2NUrvUr11zd" resolve="membership_proof_functions" />
              <ref role="3cqZAo" node="2NUrvUuvOBt" resolve="HEIGHT" />
            </node>
          </node>
          <node concept="3uNrnE" id="3FDiKWYzea8" role="1Dwrff">
            <node concept="37vLTw" id="3FDiKWYzeaa" role="2$L3a6">
              <ref role="3cqZAo" node="3FDiKWYzdSc" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="3FDiKWYzhL9" role="3cqZAp">
          <node concept="37vLTw" id="3FDiKWYzhYP" role="3cqZAk">
            <ref role="3cqZAo" node="3FDiKWYzdH7" resolve="currentDigest" />
          </node>
        </node>
      </node>
      <node concept="2D7PWU" id="3FDiKWYzdtl" role="3clF45">
        <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
      </node>
      <node concept="37vLTG" id="3FDiKWYzd$S" role="3clF46">
        <property role="TrG5h" value="leaf" />
        <node concept="2D7PWU" id="3FDiKWYzd$R" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="3Tm1VV" id="6GTEY6fFfx0" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="3FDiKWYzccb" role="jymVt" />
    <node concept="3Tm1VV" id="3FDiKWYz72z" role="1B3o_S" />
  </node>
  <node concept="312cEu" id="2NUrvUr11zd">
    <property role="TrG5h" value="membership_proof_functions" />
    <node concept="2tJIrI" id="2NUrvUuvOoU" role="jymVt" />
    <node concept="Wx3nA" id="2NUrvUuvOBt" role="jymVt">
      <property role="2dlcS1" value="false" />
      <property role="2dld4O" value="false" />
      <property role="TrG5h" value="HEIGHT" />
      <property role="3TUv4t" value="true" />
      <node concept="3Tm1VV" id="2NUrvUuvOBu" role="1B3o_S" />
      <node concept="10Oyi0" id="2NUrvUuvOBv" role="1tU5fm" />
      <node concept="3cmrfG" id="2NUrvUuvOBw" role="33vP2m">
        <property role="3cmrfH" value="4" />
      </node>
    </node>
    <node concept="2tJIrI" id="2NUrvUuvOwb" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5bZL7" role="jymVt">
      <property role="DRO8Q" value="non_membership proof function" />
    </node>
    <node concept="DJdLC" id="3h4liP5c0jl" role="jymVt">
      <property role="DRO8Q" value="It uses F_p[8] as its input_domain_name's format and takes MerkleAuthPath structure as its input format" />
    </node>
    <node concept="2YIFZL" id="2NUrvU$eYBr" role="jymVt">
      <property role="TrG5h" value="check_membership_path" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="2NUrvU$eYBs" role="3clF47">
        <node concept="3clFbH" id="2NUrvU$eYBH" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5bWfh" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWfj" role="3SKWNk">
            <property role="3SKdUp" value="compute the hash of input_domain_name in the first layer" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5bWl9" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWlb" role="3SKWNk">
            <property role="3SKdUp" value="The needs an 8-input Poseidon Hash since domain name has a larger size" />
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYBI" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYBJ" role="3cpWs9">
            <property role="TrG5h" value="leaf" />
            <node concept="2D7PWU" id="2NUrvU$eYBK" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="2NUrvU$eYBL" role="3cqZAp">
          <node concept="37vLTI" id="2NUrvU$eYBM" role="3clFbG">
            <node concept="2YIFZM" id="2NUrvU$eYBN" role="37vLTx">
              <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
              <ref role="37wK5l" to="7dh8:1y50vKi9QC$" resolve="poseidon_hash_8" />
              <node concept="37vLTw" id="2NUrvU$eYBO" role="37wK5m">
                <ref role="3cqZAo" node="2NUrvU$eYCV" resolve="input_domain_name" />
              </node>
            </node>
            <node concept="37vLTw" id="2NUrvU$eYBP" role="37vLTJ">
              <ref role="3cqZAo" node="2NUrvU$eYBJ" resolve="leaf" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2NUrvU$eYBQ" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5bWrD" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWrF" role="3SKWNk">
            <property role="3SKdUp" value="compute the Merkle Root using left_left and right_left verifiably" />
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYBR" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYBS" role="3cpWs9">
            <property role="TrG5h" value="left_root" />
            <node concept="2D7PWU" id="2NUrvU$eYBT" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="2NUrvU$eYBU" role="33vP2m">
              <node concept="liA8E" id="2NUrvU$eYBV" role="2OqNvi">
                <ref role="37wK5l" node="3FDiKWYzdx6" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="2NUrvU$eYBW" role="37wK5m">
                  <ref role="3cqZAo" node="2NUrvU$eYD0" resolve="left_leaf" />
                </node>
              </node>
              <node concept="37vLTw" id="2NUrvU$eZBL" role="2Oq$k0">
                <ref role="3cqZAo" node="2NUrvU$eYD4" resolve="authPath_left" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eZGt" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eZGu" role="3cpWs9">
            <property role="TrG5h" value="right_root" />
            <node concept="2D7PWU" id="2NUrvU$eZGv" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="2NUrvU$eZGw" role="33vP2m">
              <node concept="liA8E" id="2NUrvU$eZGx" role="2OqNvi">
                <ref role="37wK5l" node="3FDiKWYzdx6" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="2NUrvU$eZP4" role="37wK5m">
                  <ref role="3cqZAo" node="2NUrvU$eYD2" resolve="right_leaf" />
                </node>
              </node>
              <node concept="37vLTw" id="2NUrvU$eZOi" role="2Oq$k0">
                <ref role="3cqZAo" node="2NUrvU$eYDc" resolve="authPath_right" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2NUrvU$eZDR" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5bWuB" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWuD" role="3SKWNk">
            <property role="3SKdUp" value="compare that roots are the same (public input root, roots computed from left and right)" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5bW$D" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bW$F" role="3SKWNk">
            <property role="3SKdUp" value="It can prove that both left_leaf and right_leaf exist in Merkle Tree" />
          </node>
        </node>
        <node concept="3s6pcg" id="2NUrvU$eYC6" role="3cqZAp">
          <node concept="37vLTw" id="2NUrvU$eYC7" role="3s6pch">
            <ref role="3cqZAo" node="2NUrvU$eYBS" resolve="left_root" />
          </node>
          <node concept="37vLTw" id="2NUrvU$eYC8" role="3s6pci">
            <ref role="3cqZAo" node="2NUrvU$eYCY" resolve="root" />
          </node>
        </node>
        <node concept="3s6pcg" id="2NUrvU$eYC9" role="3cqZAp">
          <node concept="37vLTw" id="2NUrvU$eZPI" role="3s6pch">
            <ref role="3cqZAo" node="2NUrvU$eZGu" resolve="right_root" />
          </node>
          <node concept="37vLTw" id="2NUrvU$eYCb" role="3s6pci">
            <ref role="3cqZAo" node="2NUrvU$eYCY" resolve="root" />
          </node>
        </node>
        <node concept="3clFbH" id="2NUrvU$eYCc" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5bWEY" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWF0" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf and right_leaf are adjacent!" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5bWL8" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bWLa" role="3SKWNk">
            <property role="3SKdUp" value="The directionSelector is exactly the same as leaf's index position in first layer" />
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYCd" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYCe" role="3cpWs9">
            <property role="TrG5h" value="one" />
            <node concept="3qc1$W" id="2NUrvU$eYCf" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3cmrfG" id="2NUrvU$eYCg" role="33vP2m">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
        <node concept="3s6pcg" id="2NUrvU$eYCh" role="3cqZAp">
          <node concept="3cpWs3" id="2NUrvU$eYCi" role="3s6pch">
            <node concept="2OqwBi" id="LEx6GtBkjK" role="3uHU7B">
              <node concept="37vLTw" id="2NUrvU$eZQ9" role="2Oq$k0">
                <ref role="3cqZAo" node="2NUrvU$eYD4" resolve="authPath_left" />
              </node>
              <node concept="2OwXpG" id="7LvGvKSr0TC" role="2OqNvi">
                <ref role="2Oxat5" node="3FDiKWYzc48" resolve="directionSelector" />
              </node>
            </node>
            <node concept="37vLTw" id="2NUrvU$eYCm" role="3uHU7w">
              <ref role="3cqZAo" node="2NUrvU$eYCe" resolve="one" />
            </node>
          </node>
          <node concept="2OqwBi" id="2NUrvU$eYCn" role="3s6pci">
            <node concept="2OwXpG" id="7LvGvKSr1MT" role="2OqNvi">
              <ref role="2Oxat5" node="3FDiKWYzc48" resolve="directionSelector" />
            </node>
            <node concept="37vLTw" id="2NUrvU$eZR2" role="2Oq$k0">
              <ref role="3cqZAo" node="2NUrvU$eYDc" resolve="authPath_right" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2NUrvU$eYCq" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5bXlE" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bXlG" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf is indead smaller than right_leaf" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5bXsb" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5bXsd" role="3SKWNk">
            <property role="3SKdUp" value="We can only compare uint, so we need such convert" />
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYCr" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYCs" role="3cpWs9">
            <property role="TrG5h" value="left_leaf_uint" />
            <node concept="3qc1$W" id="2NUrvU$eYCt" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="2NUrvU$eYCu" role="33vP2m">
              <node concept="3qc1$W" id="2NUrvU$eYCv" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="2NUrvU$eYCw" role="3Sueug">
                <ref role="3cqZAo" node="2NUrvU$eYD0" resolve="left_leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYCx" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYCy" role="3cpWs9">
            <property role="TrG5h" value="right_leaf_uint" />
            <node concept="3qc1$W" id="2NUrvU$eYCz" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="2NUrvU$eYC$" role="33vP2m">
              <node concept="3qc1$W" id="2NUrvU$eYC_" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="2NUrvU$eYCA" role="3Sueug">
                <ref role="3cqZAo" node="2NUrvU$eYD2" resolve="right_leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="2NUrvU$eYCB" role="3cqZAp">
          <node concept="3cpWsn" id="2NUrvU$eYCC" role="3cpWs9">
            <property role="TrG5h" value="leaf_uint" />
            <node concept="3qc1$W" id="2NUrvU$eYCD" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="2NUrvU$eYCE" role="33vP2m">
              <node concept="3qc1$W" id="2NUrvU$eYCF" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="2NUrvU$eYCG" role="3Sueug">
                <ref role="3cqZAo" node="2NUrvU$eYBJ" resolve="leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="2DKZvD" id="2NUrvU$eYCH" role="3cqZAp">
          <node concept="3eOVzh" id="2NUrvU$eYCI" role="2DKX1R">
            <node concept="37vLTw" id="2NUrvU$eYCJ" role="3uHU7B">
              <ref role="3cqZAo" node="2NUrvU$eYCs" resolve="left_leaf_uint" />
            </node>
            <node concept="37vLTw" id="2NUrvU$eYCK" role="3uHU7w">
              <ref role="3cqZAo" node="2NUrvU$eYCC" resolve="leaf_uint" />
            </node>
          </node>
        </node>
        <node concept="2DKZvD" id="2NUrvU$eYCL" role="3cqZAp">
          <node concept="3eOVzh" id="2NUrvU$eYCM" role="2DKX1R">
            <node concept="37vLTw" id="2NUrvU$eYCN" role="3uHU7B">
              <ref role="3cqZAo" node="2NUrvU$eYCC" resolve="leaf_uint" />
            </node>
            <node concept="37vLTw" id="2NUrvU$eYCO" role="3uHU7w">
              <ref role="3cqZAo" node="2NUrvU$eYCy" resolve="right_leaf_uint" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="2NUrvU$eYCP" role="3cqZAp" />
        <node concept="3cpWs6" id="2NUrvU$eYCQ" role="3cqZAp">
          <node concept="3SuevK" id="2NUrvU$eYCR" role="3cqZAk">
            <node concept="3qc1$W" id="2NUrvU$eYCS" role="3SuevR">
              <property role="3qc1Xj" value="1" />
            </node>
            <node concept="3cmrfG" id="2NUrvU$eYCT" role="3Sueug">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3qc1$W" id="2NUrvU$eYCU" role="3clF45">
        <property role="3qc1Xj" value="1" />
      </node>
      <node concept="37vLTG" id="2NUrvU$eYCV" role="3clF46">
        <property role="TrG5h" value="input_domain_name" />
        <node concept="10Q1$e" id="2NUrvU$eYCW" role="1tU5fm">
          <node concept="2D7PWU" id="2NUrvU$eYCX" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="2NUrvU$eYCY" role="3clF46">
        <property role="TrG5h" value="root" />
        <node concept="2D7PWU" id="2NUrvU$eYCZ" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="2NUrvU$eYD0" role="3clF46">
        <property role="TrG5h" value="left_leaf" />
        <node concept="2D7PWU" id="2NUrvU$eYD1" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="2NUrvU$eYD2" role="3clF46">
        <property role="TrG5h" value="right_leaf" />
        <node concept="2D7PWU" id="2NUrvU$eYD3" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="2NUrvU$eYD4" role="3clF46">
        <property role="TrG5h" value="authPath_left" />
        <node concept="3uibUv" id="7LvGvKSqVjB" role="1tU5fm">
          <ref role="3uigEE" node="3FDiKWYz72y" resolve="MerkleAuthPath" />
        </node>
      </node>
      <node concept="37vLTG" id="2NUrvU$eYDc" role="3clF46">
        <property role="TrG5h" value="authPath_right" />
        <node concept="3uibUv" id="7LvGvKSqUW1" role="1tU5fm">
          <ref role="3uigEE" node="3FDiKWYz72y" resolve="MerkleAuthPath" />
        </node>
      </node>
      <node concept="3Tm1VV" id="2NUrvU$eYDe" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="2wUQHkdXuEX" role="jymVt" />
    <node concept="2tJIrI" id="368D951eDdt" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5c4wF" role="jymVt">
      <property role="DRO8Q" value="wildcard non_membership proof function" />
    </node>
    <node concept="DJdLC" id="3h4liP5c4wG" role="jymVt">
      <property role="DRO8Q" value="It takes uint_8[255] as its input_domain_name's format and takes Merkle Path and directionSelector directly as its input format" />
    </node>
    <node concept="2YIFZL" id="5MGqnSkqBAT" role="jymVt">
      <property role="TrG5h" value="membershipProofChecks" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="5MGqnSkqBAU" role="3clF47">
        <node concept="3SKdUt" id="2wUQHkdXvmE" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXvmG" role="3SKWNk">
            <property role="3SKdUp" value="construct MerkAuthPath structure (compute root for left_path and right_path later)" />
          </node>
        </node>
        <node concept="3cpWs8" id="5MGqnSkqBAW" role="3cqZAp">
          <node concept="3cpWsn" id="5MGqnSkqBAX" role="3cpWs9">
            <property role="TrG5h" value="authPath_left" />
            <node concept="3uibUv" id="7LvGvL8EUqJ" role="1tU5fm">
              <ref role="3uigEE" node="3FDiKWYz72y" resolve="MerkleAuthPath" />
            </node>
            <node concept="2ShNRf" id="5MGqnSkqBAZ" role="33vP2m">
              <node concept="1pGfFk" id="5MGqnSkqBB0" role="2ShVmc">
                <ref role="37wK5l" node="5MGqnSkqBqm" resolve="MerkleAuthPath" />
                <node concept="37vLTw" id="5MGqnSkqBB1" role="37wK5m">
                  <ref role="3cqZAo" node="5MGqnSkqBFl" resolve="direction" />
                </node>
                <node concept="37vLTw" id="5MGqnSkqBB2" role="37wK5m">
                  <ref role="3cqZAo" node="5MGqnSkqBFf" resolve="authPath_array" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="2wUQHkdXvzf" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXvzg" role="3SKWNk">
            <property role="3SKdUp" value="convert the left and right domain name toF_p[8] to be accepted by Poseidon Hash" />
          </node>
        </node>
        <node concept="3cpWs8" id="5MGqnSkqBBb" role="3cqZAp">
          <node concept="3cpWsn" id="5MGqnSkqBBc" role="3cpWs9">
            <property role="TrG5h" value="leaf" />
            <node concept="2D7PWU" id="5MGqnSkqBBd" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="5MGqnSkqBBe" role="3cqZAp">
          <node concept="3cpWsn" id="5MGqnSkqBBf" role="3cpWs9">
            <property role="TrG5h" value="leaf_url_fp" />
            <node concept="10Q1$e" id="5MGqnSkqBBg" role="1tU5fm">
              <node concept="2D7PWU" id="5MGqnSkqBBh" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="1rXfSq" id="5MGqnSkqBBi" role="33vP2m">
              <ref role="37wK5l" node="368D9512hNj" resolve="convert_8_to_Fp_python" />
              <node concept="37vLTw" id="5MGqnSkqBBj" role="37wK5m">
                <ref role="3cqZAo" node="5MGqnSkqBF5" resolve="leaf_url" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5MGqnSkqBC2" role="3cqZAp" />
        <node concept="3SKdUt" id="2wUQHkdXvPO" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXvPP" role="3SKWNk">
            <property role="3SKdUp" value="compute the hash of left and right domain name in the first layer" />
          </node>
        </node>
        <node concept="3SKdUt" id="2wUQHkdXvPQ" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXvPR" role="3SKWNk">
            <property role="3SKdUp" value="The needs an 8-input Poseidon Hash since domain name has a larger size" />
          </node>
        </node>
        <node concept="3cpWs8" id="5MGqnSkqBCE" role="3cqZAp">
          <node concept="3cpWsn" id="5MGqnSkqBCF" role="3cpWs9">
            <property role="TrG5h" value="leaf_hash" />
            <node concept="2YIFZM" id="5MGqnSkqBCG" role="33vP2m">
              <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
              <ref role="37wK5l" to="7dh8:1y50vKi9QC$" resolve="poseidon_hash_8" />
              <node concept="37vLTw" id="5MGqnSkqBCH" role="37wK5m">
                <ref role="3cqZAo" node="5MGqnSkqBBf" resolve="leaf_url_fp" />
              </node>
            </node>
            <node concept="2D7PWU" id="5MGqnSkqBCI" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5MGqnSkqBCO" role="3cqZAp" />
        <node concept="3SKdUt" id="2wUQHkdXw8B" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXw8C" role="3SKWNk">
            <property role="3SKdUp" value="compute the Merkle Root using left_left and right_left verifiably" />
          </node>
        </node>
        <node concept="3cpWs8" id="5MGqnSkqBD0" role="3cqZAp">
          <node concept="3cpWsn" id="5MGqnSkqBD1" role="3cpWs9">
            <property role="TrG5h" value="computed_root" />
            <node concept="2D7PWU" id="5MGqnSkqBD2" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="5MGqnSkqBD3" role="33vP2m">
              <node concept="37vLTw" id="5MGqnSkqBD4" role="2Oq$k0">
                <ref role="3cqZAo" node="5MGqnSkqBAX" resolve="authPath_left" />
              </node>
              <node concept="liA8E" id="5MGqnSkqBD5" role="2OqNvi">
                <ref role="37wK5l" node="3FDiKWYzdx6" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="5MGqnSkqBD6" role="37wK5m">
                  <ref role="3cqZAo" node="5MGqnSkqBCF" resolve="leaf_hash" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5MGqnSkqBDe" role="3cqZAp" />
        <node concept="3SKdUt" id="2wUQHkdXwru" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXwrv" role="3SKWNk">
            <property role="3SKdUp" value="compare that roots are the same (public input root, roots computed from left and right)" />
          </node>
        </node>
        <node concept="3SKdUt" id="2wUQHkdXwrw" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXwrx" role="3SKWNk">
            <property role="3SKdUp" value="It can prove that both left_leaf and right_leaf exist in Merkle Tree" />
          </node>
        </node>
        <node concept="1X3_iC" id="72Di_tqJKGs" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="3xVcAt3NgjR" role="8Wnug">
            <node concept="Xl_RD" id="3xVcAt3NgjS" role="vCCx3">
              <property role="Xl_RC" value="root" />
            </node>
            <node concept="37vLTw" id="3xVcAt3NgjT" role="vCCWX">
              <ref role="3cqZAo" node="5MGqnSkqBF3" resolve="root" />
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="72Di_tqJKGt" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="3xVcAt3L0Tu" role="8Wnug">
            <node concept="Xl_RD" id="3xVcAt3L0Y0" role="vCCx3">
              <property role="Xl_RC" value="computed root" />
            </node>
            <node concept="37vLTw" id="3xVcAt3Ngor" role="vCCWX">
              <ref role="3cqZAo" node="5MGqnSkqBD1" resolve="computed_root" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="3xVcAt3Ngg9" role="3cqZAp" />
        <node concept="3s6pcg" id="5MGqnSkqBDn" role="3cqZAp">
          <node concept="37vLTw" id="5MGqnSkqBDo" role="3s6pch">
            <ref role="3cqZAo" node="5MGqnSkqBD1" resolve="computed_root" />
          </node>
          <node concept="37vLTw" id="5MGqnSkqBDp" role="3s6pci">
            <ref role="3cqZAo" node="5MGqnSkqBF3" resolve="root" />
          </node>
        </node>
        <node concept="3clFbH" id="5MGqnSkqBDF" role="3cqZAp" />
        <node concept="3SKdUt" id="2wUQHkdXx1O" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXx1P" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf is indead smaller than right_leaf" />
          </node>
        </node>
        <node concept="3SKdUt" id="2wUQHkdXx1Q" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXx1R" role="3SKWNk">
            <property role="3SKdUp" value="This firstly checks left_index and right_index for exact matching and then checks the next is smaller" />
          </node>
        </node>
        <node concept="3SKdUt" id="2wUQHkdXx1S" role="3cqZAp">
          <node concept="3SKdUq" id="2wUQHkdXx1T" role="3SKWNk">
            <property role="3SKdUp" value="check out the notes for the algorithm" />
          </node>
        </node>
        <node concept="1X3_iC" id="72Di_tqJKCg" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="3xVcAt3RQ7C" role="8Wnug">
            <node concept="37vLTw" id="3xVcAt3RQc6" role="vCCWX">
              <ref role="3cqZAo" node="5MGqnSkqBFb" resolve="leaf_length" />
            </node>
            <node concept="Xl_RD" id="3xVcAt3RQcu" role="vCCx3">
              <property role="Xl_RC" value="index" />
            </node>
          </node>
        </node>
        <node concept="1Dw8fO" id="5MGqnSkqBDG" role="3cqZAp">
          <node concept="3clFbS" id="5MGqnSkqBDH" role="2LFqv$">
            <node concept="3clFbJ" id="5MGqnSkqBDI" role="3cqZAp">
              <node concept="3clFbS" id="5MGqnSkqBDJ" role="3clFbx">
                <node concept="1X3_iC" id="72Di_tqJKB_" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="3xVcAt3Pw0w" role="8Wnug">
                    <node concept="AH0OO" id="3xVcAt3Pw1h" role="vCCWX">
                      <node concept="37vLTw" id="3xVcAt3Pw22" role="AHEQo">
                        <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="3xVcAt3Pw0W" role="AHHXb">
                        <ref role="3cqZAo" node="5MGqnSkqBF5" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="3xVcAt3Pw3e" role="vCCx3">
                      <property role="Xl_RC" value="leaf_url" />
                    </node>
                  </node>
                </node>
                <node concept="1X3_iC" id="72Di_tqJKBA" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="3xVcAt3Pw3F" role="8Wnug">
                    <node concept="AH0OO" id="3xVcAt3Pw3G" role="vCCWX">
                      <node concept="37vLTw" id="3xVcAt3Pw3H" role="AHEQo">
                        <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="3xVcAt3Pw5K" role="AHHXb">
                        <ref role="3cqZAo" node="5MGqnSkqBF0" resolve="input_domain_name_wildcard" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="3xVcAt3Pw6W" role="vCCx3">
                      <property role="Xl_RC" value="input domain wildcard" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbH" id="3xVcAt3Pw3r" role="3cqZAp" />
                <node concept="3s6pcg" id="5MGqnSkqBDK" role="3cqZAp">
                  <node concept="AH0OO" id="5MGqnSkqBDL" role="3s6pch">
                    <node concept="37vLTw" id="5MGqnSkqBDM" role="AHEQo">
                      <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="5MGqnSkqBDN" role="AHHXb">
                      <ref role="3cqZAo" node="5MGqnSkqBF5" resolve="leaf_url" />
                    </node>
                  </node>
                  <node concept="AH0OO" id="5MGqnSkqBDO" role="3s6pci">
                    <node concept="37vLTw" id="5MGqnSkqBDP" role="AHEQo">
                      <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="5MGqnSkqBDQ" role="AHHXb">
                      <ref role="3cqZAo" node="5MGqnSkqBF0" resolve="input_domain_name_wildcard" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eOVzh" id="5MGqnSkqBDR" role="3clFbw">
                <node concept="3SuevK" id="5MGqnSkqBDS" role="3uHU7B">
                  <node concept="3qc1$W" id="5MGqnSkqBDT" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="37vLTw" id="5MGqnSkqBDU" role="3Sueug">
                    <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                  </node>
                </node>
                <node concept="37vLTw" id="5MGqnSkqBDV" role="3uHU7w">
                  <ref role="3cqZAo" node="5MGqnSkqBFb" resolve="leaf_length" />
                </node>
              </node>
              <node concept="3eNFk2" id="5MGqnSkqBDW" role="3eNLev">
                <node concept="2_lxnS" id="5MGqnSkqBDX" role="3eO9$A">
                  <node concept="3SuevK" id="5MGqnSkqBDY" role="3uHU7B">
                    <node concept="3qc1$W" id="5MGqnSkqBDZ" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="37vLTw" id="5MGqnSkqBE0" role="3Sueug">
                      <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                    </node>
                  </node>
                  <node concept="37vLTw" id="5MGqnSkqBE1" role="3uHU7w">
                    <ref role="3cqZAo" node="5MGqnSkqBFb" resolve="leaf_length" />
                  </node>
                </node>
                <node concept="3clFbS" id="5MGqnSkqBE2" role="3eOfB_">
                  <node concept="3s6pcg" id="3xVcAt3Ua9g" role="3cqZAp">
                    <node concept="AH0OO" id="3xVcAt3Uaah" role="3s6pch">
                      <node concept="37vLTw" id="3xVcAt3Uab4" role="AHEQo">
                        <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="3xVcAt3YoLt" role="AHHXb">
                        <ref role="3cqZAo" node="5MGqnSkqBF5" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="3SuevK" id="3xVcAt3UagF" role="3s6pci">
                      <node concept="3qc1$W" id="3xVcAt3UagH" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="2nou5x" id="3xVcAt3UahH" role="3Sueug">
                        <property role="2noCCI" value="00" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="5MGqnSkqBEL" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="5MGqnSkqBEM" role="1tU5fm" />
            <node concept="3cmrfG" id="5MGqnSkqBEN" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="5MGqnSkqBEO" role="1Dwp0S">
            <node concept="3cmrfG" id="5MGqnSkqBEP" role="3uHU7w">
              <property role="3cmrfH" value="255" />
            </node>
            <node concept="37vLTw" id="5MGqnSkqBEQ" role="3uHU7B">
              <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="5MGqnSkqBER" role="1Dwrff">
            <node concept="37vLTw" id="5MGqnSkqBES" role="2$L3a6">
              <ref role="3cqZAo" node="5MGqnSkqBEL" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="5MGqnSkqBET" role="3cqZAp" />
        <node concept="3cpWs6" id="5MGqnSkqBEU" role="3cqZAp">
          <node concept="3SuevK" id="5MGqnSkqBEV" role="3cqZAk">
            <node concept="3qc1$W" id="5MGqnSkqBEW" role="3SuevR">
              <property role="3qc1Xj" value="1" />
            </node>
            <node concept="3cmrfG" id="5MGqnSkqBEX" role="3Sueug">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3qc1$W" id="5MGqnSkqBEZ" role="3clF45">
        <property role="3qc1Xj" value="1" />
      </node>
      <node concept="37vLTG" id="5MGqnSkqBF0" role="3clF46">
        <property role="TrG5h" value="input_domain_name_wildcard" />
        <node concept="10Q1$e" id="5MGqnSkqBF1" role="1tU5fm">
          <node concept="3qc1$W" id="5MGqnSkqBF2" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBF3" role="3clF46">
        <property role="TrG5h" value="root" />
        <node concept="2D7PWU" id="5MGqnSkqBF4" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBF5" role="3clF46">
        <property role="TrG5h" value="leaf_url" />
        <node concept="10Q1$e" id="5MGqnSkqBF6" role="1tU5fm">
          <node concept="3qc1$W" id="5MGqnSkqBF7" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBFb" role="3clF46">
        <property role="TrG5h" value="leaf_length" />
        <node concept="3qc1$W" id="5MGqnSkqBFc" role="1tU5fm">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBFf" role="3clF46">
        <property role="TrG5h" value="authPath_array" />
        <node concept="10Q1$e" id="5MGqnSkqBFg" role="1tU5fm">
          <node concept="2D7PWU" id="5MGqnSkqBFh" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="5MGqnSkqBFl" role="3clF46">
        <property role="TrG5h" value="direction" />
        <node concept="3qc1$W" id="5MGqnSkqBFm" role="1tU5fm">
          <property role="3qc1Xj" value="64" />
        </node>
      </node>
      <node concept="3Tm1VV" id="5MGqnSkqBFp" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="7LvGvL8ETzG" role="jymVt" />
    <node concept="DJdLC" id="3h4liP5cbHN" role="jymVt">
      <property role="DRO8Q" value="convert uint_8[] to F_p[8] -- the same as python script" />
    </node>
    <node concept="2YIFZL" id="368D9512hNj" role="jymVt">
      <property role="TrG5h" value="convert_8_to_Fp_python" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="368D9512hNk" role="3clF47">
        <node concept="3clFbH" id="368D9512hNl" role="3cqZAp" />
        <node concept="3cpWs8" id="368D9512hNm" role="3cqZAp">
          <node concept="3cpWsn" id="368D9512hNn" role="3cpWs9">
            <property role="TrG5h" value="Fp_array" />
            <node concept="10Q1$e" id="368D9512hNo" role="1tU5fm">
              <node concept="2D7PWU" id="368D9512hNp" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="2ShNRf" id="368D9512hNq" role="33vP2m">
              <node concept="3$_iS1" id="368D9512hNr" role="2ShVmc">
                <node concept="3$GHV9" id="368D9512hNs" role="3$GQph">
                  <node concept="3cmrfG" id="368D9512hNt" role="3$I4v7">
                    <property role="3cmrfH" value="8" />
                  </node>
                </node>
                <node concept="2D7PWU" id="368D9512hNu" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="368D9512ebs" role="3cqZAp">
          <node concept="3cpWsn" id="368D9512ebv" role="3cpWs9">
            <property role="TrG5h" value="large_int" />
            <node concept="3qc1$W" id="368D9512ebq" role="1tU5fm">
              <property role="3qc1Xj" value="2040" />
            </node>
            <node concept="3SuevK" id="368D9512eOt" role="33vP2m">
              <node concept="3qc1$W" id="368D9512eOv" role="3SuevR">
                <property role="3qc1Xj" value="2040" />
              </node>
              <node concept="3cmrfG" id="368D9512eR8" role="3Sueug">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="368D9517lg$" role="3cqZAp">
          <node concept="3cpWsn" id="368D9517lgB" role="3cpWs9">
            <property role="TrG5h" value="tempt" />
            <node concept="3qc1$W" id="368D9517lgy" role="1tU5fm">
              <property role="3qc1Xj" value="2040" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="368D9512ehR" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5ceOE" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5ceOG" role="3SKWNk">
            <property role="3SKdUp" value="concate uint_8[] to get uint_2040" />
          </node>
        </node>
        <node concept="1Dw8fO" id="368D9512equ" role="3cqZAp">
          <node concept="3clFbS" id="368D9512eqw" role="2LFqv$">
            <node concept="3clFbF" id="368D9517lmA" role="3cqZAp">
              <node concept="37vLTI" id="368D9517lnA" role="3clFbG">
                <node concept="3SuevK" id="368D9517loe" role="37vLTx">
                  <node concept="3qc1$W" id="368D9517log" role="3SuevR">
                    <property role="3qc1Xj" value="2040" />
                  </node>
                  <node concept="1GRDU$" id="368D9517ltu" role="3Sueug">
                    <node concept="1eOMI4" id="368D9517luA" role="3uHU7w">
                      <node concept="17qRlL" id="368D9517l_J" role="1eOMHV">
                        <node concept="37vLTw" id="368D9517l_W" role="3uHU7w">
                          <ref role="3cqZAo" node="368D9512eqx" resolve="i" />
                        </node>
                        <node concept="3cmrfG" id="368D9517lvI" role="3uHU7B">
                          <property role="3cmrfH" value="8" />
                        </node>
                      </node>
                    </node>
                    <node concept="3SuevK" id="368D9517lp7" role="3uHU7B">
                      <node concept="3qc1$W" id="368D9517lp9" role="3SuevR">
                        <property role="3qc1Xj" value="2040" />
                      </node>
                      <node concept="AH0OO" id="368D9517lrf" role="3Sueug">
                        <node concept="37vLTw" id="368D9517lsl" role="AHEQo">
                          <ref role="3cqZAo" node="368D9512eqx" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="368D9517lqb" role="AHHXb">
                          <ref role="3cqZAo" node="368D9512hUA" resolve="byte_array" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
                <node concept="37vLTw" id="368D9517lm$" role="37vLTJ">
                  <ref role="3cqZAo" node="368D9517lgB" resolve="tempt" />
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="368D9512eRH" role="3cqZAp">
              <node concept="37vLTI" id="368D9512eSz" role="3clFbG">
                <node concept="3cpWs3" id="368D9512eUj" role="37vLTx">
                  <node concept="37vLTw" id="368D9512eTm" role="3uHU7B">
                    <ref role="3cqZAo" node="368D9512ebv" resolve="large_int" />
                  </node>
                  <node concept="37vLTw" id="368D9517lP8" role="3uHU7w">
                    <ref role="3cqZAo" node="368D9517lgB" resolve="tempt" />
                  </node>
                </node>
                <node concept="37vLTw" id="368D9512eRF" role="37vLTJ">
                  <ref role="3cqZAo" node="368D9512ebv" resolve="large_int" />
                </node>
              </node>
            </node>
            <node concept="3clFbH" id="368D9515EUt" role="3cqZAp" />
          </node>
          <node concept="3cpWsn" id="368D9512eqx" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="368D9512euV" role="1tU5fm" />
            <node concept="3cmrfG" id="368D9512evz" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="368D9512e_I" role="1Dwp0S">
            <node concept="3cmrfG" id="368D9512e_V" role="3uHU7w">
              <property role="3cmrfH" value="255" />
            </node>
            <node concept="37vLTw" id="368D9512ewf" role="3uHU7B">
              <ref role="3cqZAo" node="368D9512eqx" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="368D9512eKR" role="1Dwrff">
            <node concept="37vLTw" id="368D9512eKT" role="2$L3a6">
              <ref role="3cqZAo" node="368D9512eqx" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5cfrl" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cfrn" role="3SKWNk">
            <property role="3SKdUp" value=" decompse uint_2040 into bit array" />
          </node>
        </node>
        <node concept="3cpWs8" id="368D951935p" role="3cqZAp">
          <node concept="3cpWsn" id="368D951935s" role="3cpWs9">
            <property role="TrG5h" value="bit_array" />
            <node concept="10Q1$e" id="368D951938p" role="1tU5fm">
              <node concept="1QD1ZQ" id="368D951935n" role="10Q1$1" />
            </node>
            <node concept="2ShNRf" id="368D95193aY" role="33vP2m">
              <node concept="3$_iS1" id="368D95194fm" role="2ShVmc">
                <node concept="3$GHV9" id="368D95194fo" role="3$GQph">
                  <node concept="3cmrfG" id="368D95194gc" role="3$I4v7">
                    <property role="3cmrfH" value="2040" />
                  </node>
                </node>
                <node concept="1QD1ZQ" id="368D95194fg" role="3$_nBY" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="368D95194uf" role="3cqZAp">
          <node concept="37vLTI" id="368D95194xH" role="3clFbG">
            <node concept="37vLTw" id="368D95194ud" role="37vLTJ">
              <ref role="3cqZAo" node="368D951935s" resolve="bit_array" />
            </node>
            <node concept="2OqwBi" id="368D95194Au" role="37vLTx">
              <node concept="37vLTw" id="368D95194_e" role="2Oq$k0">
                <ref role="3cqZAo" node="368D9512ebv" resolve="large_int" />
              </node>
              <node concept="1VPAEj" id="368D95194Bj" role="2OqNvi" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="368D95194Cc" role="3cqZAp" />
        <node concept="3clFbH" id="368D95195NH" role="3cqZAp" />
        <node concept="3cpWs8" id="368D951e$1A" role="3cqZAp">
          <node concept="3cpWsn" id="368D951e$1B" role="3cpWs9">
            <property role="TrG5h" value="Fp_buffer" />
            <node concept="3qc1$W" id="368D951e$1C" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
            <node concept="3SuevK" id="368D951e$1D" role="33vP2m">
              <node concept="3qc1$W" id="368D951e$1E" role="3SuevR">
                <property role="3qc1Xj" value="254" />
              </node>
              <node concept="3cmrfG" id="368D951e$1F" role="3Sueug">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="368D95195VI" role="3cqZAp">
          <node concept="3cpWsn" id="368D95195VJ" role="3cpWs9">
            <property role="TrG5h" value="bit_tempt" />
            <node concept="3qc1$W" id="368D95195VK" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="368D95195VL" role="3cqZAp">
          <node concept="3cpWsn" id="368D95195VM" role="3cpWs9">
            <property role="TrG5h" value="Fp_tempt" />
            <node concept="3qc1$W" id="368D95195VN" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="368D95195RA" role="3cqZAp" />
        <node concept="3SKdUt" id="3h4liP5cfIE" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cfIF" role="3SKWNk">
            <property role="3SKdUp" value="recombine bit array into F_p array" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5cfIG" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cfIH" role="3SKWNk">
            <property role="3SKdUp" value="254 bit will convert to one F_p element" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5cfII" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cfIJ" role="3SKWNk">
            <property role="3SKdUp" value="Note that it's not secure to convert 254bit directly since F_p is also 254 bit" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5cfIK" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cfIL" role="3SKWNk">
            <property role="3SKdUp" value="Implementation needs to be tuned (revise it to 253bit or increase F_p)" />
          </node>
        </node>
        <node concept="3SKdUt" id="3h4liP5cgdb" role="3cqZAp">
          <node concept="3SKdUq" id="3h4liP5cgdd" role="3SKWNk">
            <property role="3SKdUp" value="It's enough to support 253bytes input since it's the largest size for input domain name" />
          </node>
        </node>
        <node concept="1Dw8fO" id="368D951963u" role="3cqZAp">
          <node concept="3clFbS" id="368D951963v" role="2LFqv$">
            <node concept="1Dw8fO" id="368D951963w" role="3cqZAp">
              <node concept="3clFbS" id="368D951963x" role="2LFqv$">
                <node concept="3SKdUt" id="368D951963y" role="3cqZAp">
                  <node concept="3SKdUq" id="368D951963z" role="3SKWNk">
                    <property role="3SKdUp" value="get the bit" />
                  </node>
                </node>
                <node concept="3clFbF" id="368D951963$" role="3cqZAp">
                  <node concept="37vLTI" id="368D951963_" role="3clFbG">
                    <node concept="3SuevK" id="368D951963A" role="37vLTx">
                      <node concept="3qc1$W" id="368D951963B" role="3SuevR">
                        <property role="3qc1Xj" value="254" />
                      </node>
                      <node concept="AH0OO" id="368D951963C" role="3Sueug">
                        <node concept="3cpWs3" id="368D951963D" role="AHEQo">
                          <node concept="37vLTw" id="368D951963E" role="3uHU7w">
                            <ref role="3cqZAo" node="368D951965h" resolve="j" />
                          </node>
                          <node concept="17qRlL" id="368D951963F" role="3uHU7B">
                            <node concept="37vLTw" id="368D951963G" role="3uHU7B">
                              <ref role="3cqZAo" node="368D951965J" resolve="i" />
                            </node>
                            <node concept="3cmrfG" id="368D951963H" role="3uHU7w">
                              <property role="3cmrfH" value="253" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="368D95196pn" role="AHHXb">
                          <ref role="3cqZAo" node="368D951935s" resolve="bit_array" />
                        </node>
                      </node>
                    </node>
                    <node concept="37vLTw" id="368D951963J" role="37vLTJ">
                      <ref role="3cqZAo" node="368D95195VJ" resolve="bit_tempt" />
                    </node>
                  </node>
                </node>
                <node concept="3SKdUt" id="368D951963K" role="3cqZAp">
                  <node concept="3SKdUq" id="368D951963L" role="3SKWNk">
                    <property role="3SKdUp" value="left shift this bit" />
                  </node>
                </node>
                <node concept="3clFbF" id="368D951963V" role="3cqZAp">
                  <node concept="37vLTI" id="368D951963W" role="3clFbG">
                    <node concept="37vLTw" id="368D951963X" role="37vLTx">
                      <ref role="3cqZAo" node="368D95195VJ" resolve="bit_tempt" />
                    </node>
                    <node concept="37vLTw" id="368D951963Y" role="37vLTJ">
                      <ref role="3cqZAo" node="368D95195VM" resolve="Fp_tempt" />
                    </node>
                  </node>
                </node>
                <node concept="1Dw8fO" id="368D951963Z" role="3cqZAp">
                  <node concept="3clFbS" id="368D9519640" role="2LFqv$">
                    <node concept="3clFbF" id="368D9519641" role="3cqZAp">
                      <node concept="37vLTI" id="368D9519642" role="3clFbG">
                        <node concept="17qRlL" id="368D9519643" role="37vLTx">
                          <node concept="37vLTw" id="368D9519644" role="3uHU7B">
                            <ref role="3cqZAo" node="368D95195VM" resolve="Fp_tempt" />
                          </node>
                          <node concept="3SuevK" id="368D9519645" role="3uHU7w">
                            <node concept="3qc1$W" id="368D9519646" role="3SuevR">
                              <property role="3qc1Xj" value="2" />
                            </node>
                            <node concept="3cmrfG" id="368D9519647" role="3Sueug">
                              <property role="3cmrfH" value="2" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="368D9519648" role="37vLTJ">
                          <ref role="3cqZAo" node="368D95195VM" resolve="Fp_tempt" />
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWsn" id="368D9519649" role="1Duv9x">
                    <property role="TrG5h" value="k" />
                    <node concept="10Oyi0" id="368D951964a" role="1tU5fm" />
                    <node concept="3cmrfG" id="368D951964b" role="33vP2m">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3eOVzh" id="368D951964c" role="1Dwp0S">
                    <node concept="37vLTw" id="368D951964e" role="3uHU7w">
                      <ref role="3cqZAo" node="368D951965h" resolve="j" />
                    </node>
                    <node concept="37vLTw" id="368D951964g" role="3uHU7B">
                      <ref role="3cqZAo" node="368D9519649" resolve="k" />
                    </node>
                  </node>
                  <node concept="3uNrnE" id="368D951964h" role="1Dwrff">
                    <node concept="37vLTw" id="368D951964i" role="2$L3a6">
                      <ref role="3cqZAo" node="368D9519649" resolve="k" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbF" id="368D951964S" role="3cqZAp">
                  <node concept="37vLTI" id="368D951964T" role="3clFbG">
                    <node concept="37vLTw" id="368D951964U" role="37vLTJ">
                      <ref role="3cqZAo" node="368D951e$1B" resolve="Fp_buffer" />
                    </node>
                    <node concept="3cpWs3" id="368D951964V" role="37vLTx">
                      <node concept="37vLTw" id="368D951964W" role="3uHU7w">
                        <ref role="3cqZAo" node="368D951e$1B" resolve="Fp_buffer" />
                      </node>
                      <node concept="37vLTw" id="368D951964X" role="3uHU7B">
                        <ref role="3cqZAo" node="368D95195VM" resolve="Fp_tempt" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3cpWsn" id="368D951965h" role="1Duv9x">
                <property role="TrG5h" value="j" />
                <node concept="10Oyi0" id="368D951965i" role="1tU5fm" />
                <node concept="3cmrfG" id="368D951965j" role="33vP2m">
                  <property role="3cmrfH" value="0" />
                </node>
              </node>
              <node concept="3eOVzh" id="368D951965k" role="1Dwp0S">
                <node concept="3cmrfG" id="368D951965l" role="3uHU7w">
                  <property role="3cmrfH" value="253" />
                </node>
                <node concept="37vLTw" id="368D951965m" role="3uHU7B">
                  <ref role="3cqZAo" node="368D951965h" resolve="j" />
                </node>
              </node>
              <node concept="3uNrnE" id="368D951965n" role="1Dwrff">
                <node concept="37vLTw" id="368D951965o" role="2$L3a6">
                  <ref role="3cqZAo" node="368D951965h" resolve="j" />
                </node>
              </node>
            </node>
            <node concept="3SKdUt" id="368D951965p" role="3cqZAp">
              <node concept="3SKdUq" id="368D951965q" role="3SKWNk">
                <property role="3SKdUp" value="turn uint254 to Fp" />
              </node>
            </node>
            <node concept="3clFbF" id="368D951965r" role="3cqZAp">
              <node concept="37vLTI" id="368D951965s" role="3clFbG">
                <node concept="AH0OO" id="368D951965t" role="37vLTJ">
                  <node concept="37vLTw" id="368D951965u" role="AHEQo">
                    <ref role="3cqZAo" node="368D951965J" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="368D951965v" role="AHHXb">
                    <ref role="3cqZAo" node="368D9512hNn" resolve="Fp_array" />
                  </node>
                </node>
                <node concept="_hXgR" id="368D951965w" role="37vLTx">
                  <node concept="2D7PWU" id="368D951965x" role="_hXgQ">
                    <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                  </node>
                  <node concept="37vLTw" id="368D951965y" role="_hXgL">
                    <ref role="3cqZAo" node="368D951e$1B" resolve="Fp_buffer" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="368D951965z" role="3cqZAp">
              <node concept="37vLTI" id="368D951965$" role="3clFbG">
                <node concept="3cmrfG" id="368D951965_" role="37vLTx">
                  <property role="3cmrfH" value="0" />
                </node>
                <node concept="37vLTw" id="368D951965A" role="37vLTJ">
                  <ref role="3cqZAo" node="368D951e$1B" resolve="Fp_buffer" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="368D951965J" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="368D951965K" role="1tU5fm" />
            <node concept="3cmrfG" id="368D951965L" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="368D951965M" role="1Dwp0S">
            <node concept="3cmrfG" id="368D951965N" role="3uHU7w">
              <property role="3cmrfH" value="8" />
            </node>
            <node concept="37vLTw" id="368D951965O" role="3uHU7B">
              <ref role="3cqZAo" node="368D951965J" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="368D951965P" role="1Dwrff">
            <node concept="37vLTw" id="368D951965Q" role="2$L3a6">
              <ref role="3cqZAo" node="368D951965J" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="368D95195qD" role="3cqZAp" />
        <node concept="3clFbH" id="368D95195o1" role="3cqZAp" />
        <node concept="3cpWs6" id="368D9512hUx" role="3cqZAp">
          <node concept="37vLTw" id="368D9512hUy" role="3cqZAk">
            <ref role="3cqZAo" node="368D9512hNn" resolve="Fp_array" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="368D9512hUz" role="1B3o_S" />
      <node concept="10Q1$e" id="368D9512hU$" role="3clF45">
        <node concept="2D7PWU" id="368D9512hU_" role="10Q1$1">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="368D9512hUA" role="3clF46">
        <property role="TrG5h" value="byte_array" />
        <node concept="10Q1$e" id="368D9512hUB" role="1tU5fm">
          <node concept="3qc1$W" id="368D9512hUC" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="368D951ezOY" role="jymVt" />
    <node concept="2tJIrI" id="368D9512daz" role="jymVt" />
    <node concept="2tJIrI" id="2NUrvUr11zH" role="jymVt" />
    <node concept="3Tm1VV" id="2NUrvUr11ze" role="1B3o_S" />
  </node>
  <node concept="2VwbHx" id="4szZ9Zm1_2v">
    <property role="TrG5h" value="Test_MerkleAuthPath" />
    <node concept="DJdLC" id="4szZ9Zm1_2w" role="jymVt">
      <property role="DRO8Q" value="directionSelector chooses the direction of Merkle Path (left or right concatenation)" />
    </node>
    <node concept="DJdLC" id="4szZ9Zm1_2x" role="jymVt">
      <property role="DRO8Q" value="directionSelector also happens to be the index of leaf in the first layer" />
    </node>
    <node concept="312cEg" id="4szZ9Zm1_2y" role="jymVt">
      <property role="34CwA1" value="false" />
      <property role="eg7rD" value="false" />
      <property role="TrG5h" value="directionSelector" />
      <property role="3TUv4t" value="false" />
      <node concept="3qc1$W" id="4szZ9Zm1_2z" role="1tU5fm">
        <property role="3qc1Xj" value="64" />
      </node>
      <node concept="3Tm1VV" id="4szZ9Zm1_2$" role="1B3o_S" />
    </node>
    <node concept="DJdLC" id="4szZ9Zm1_2_" role="jymVt">
      <property role="DRO8Q" value="digests stores all the hashes in a Merkle Path" />
    </node>
    <node concept="312cEg" id="4szZ9Zm1_2A" role="jymVt">
      <property role="34CwA1" value="false" />
      <property role="eg7rD" value="false" />
      <property role="TrG5h" value="digests" />
      <property role="3TUv4t" value="false" />
      <node concept="10Q1$e" id="4szZ9Zm1_2B" role="1tU5fm">
        <node concept="2D7PWU" id="4szZ9Zm1_2C" role="10Q1$1">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="3Tm1VV" id="4szZ9Zm1_2D" role="1B3o_S" />
      <node concept="2ShNRf" id="4szZ9Zm1_2E" role="33vP2m">
        <node concept="3$_iS1" id="4szZ9Zm1_2F" role="2ShVmc">
          <node concept="3$GHV9" id="4szZ9Zm1_2G" role="3$GQph">
            <node concept="10M0yZ" id="4szZ9Zm1_2H" role="3$I4v7">
              <ref role="1PxDUh" node="1siqNxHAann" resolve="test_membership_proof_functions" />
              <ref role="3cqZAo" node="1siqNxHAhae" resolve="HEIGHT" />
            </node>
          </node>
          <node concept="2D7PWU" id="4szZ9Zm1_2I" role="3$_nBY">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="4szZ9Zm1_2J" role="jymVt" />
    <node concept="DJdLC" id="4szZ9Zm1_2K" role="jymVt">
      <property role="DRO8Q" value="initialization" />
    </node>
    <node concept="3clFbW" id="4szZ9Zm1_2L" role="jymVt">
      <node concept="3cqZAl" id="4szZ9Zm1_2M" role="3clF45" />
      <node concept="3clFbS" id="4szZ9Zm1_2N" role="3clF47">
        <node concept="3clFbF" id="4szZ9Zm1_2O" role="3cqZAp">
          <node concept="37vLTI" id="4szZ9Zm1_2P" role="3clFbG">
            <node concept="2ShNRf" id="4szZ9Zm1_2Q" role="37vLTx">
              <node concept="3$_iS1" id="4szZ9Zm1_2R" role="2ShVmc">
                <node concept="3$GHV9" id="4szZ9Zm1_2S" role="3$GQph">
                  <node concept="10M0yZ" id="4szZ9Zm1_2T" role="3$I4v7">
                    <ref role="1PxDUh" node="1siqNxHAann" resolve="test_membership_proof_functions" />
                    <ref role="3cqZAo" node="1siqNxHAhae" resolve="HEIGHT" />
                  </node>
                </node>
                <node concept="2D7PWU" id="4szZ9Zm1_2U" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
            <node concept="37vLTw" id="4szZ9Zm1_2V" role="37vLTJ">
              <ref role="3cqZAo" node="4szZ9Zm1_2A" resolve="digests" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4szZ9Zm1_2W" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="4szZ9Zm1_2X" role="jymVt" />
    <node concept="DJdLC" id="4szZ9Zm1_2Y" role="jymVt">
      <property role="DRO8Q" value="initialization" />
    </node>
    <node concept="3clFbW" id="4szZ9Zm1_2Z" role="jymVt">
      <node concept="3cqZAl" id="4szZ9Zm1_30" role="3clF45" />
      <node concept="3clFbS" id="4szZ9Zm1_31" role="3clF47">
        <node concept="3clFbF" id="4szZ9Zm1_32" role="3cqZAp">
          <node concept="37vLTI" id="4szZ9Zm1_33" role="3clFbG">
            <node concept="37vLTw" id="4szZ9Zm1_34" role="37vLTx">
              <ref role="3cqZAo" node="4szZ9Zm1_3b" resolve="inputDS" />
            </node>
            <node concept="37vLTw" id="4szZ9Zm1_35" role="37vLTJ">
              <ref role="3cqZAo" node="4szZ9Zm1_2y" resolve="directionSelector" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="4szZ9Zm1_36" role="3cqZAp">
          <node concept="37vLTI" id="4szZ9Zm1_37" role="3clFbG">
            <node concept="37vLTw" id="4szZ9Zm1_38" role="37vLTx">
              <ref role="3cqZAo" node="4szZ9Zm1_3d" resolve="inputDigests" />
            </node>
            <node concept="37vLTw" id="4szZ9Zm1_39" role="37vLTJ">
              <ref role="3cqZAo" node="4szZ9Zm1_2A" resolve="digests" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="4szZ9Zm1_3a" role="1B3o_S" />
      <node concept="37vLTG" id="4szZ9Zm1_3b" role="3clF46">
        <property role="TrG5h" value="inputDS" />
        <node concept="3qc1$W" id="4szZ9Zm1_3c" role="1tU5fm">
          <property role="3qc1Xj" value="64" />
        </node>
      </node>
      <node concept="37vLTG" id="4szZ9Zm1_3d" role="3clF46">
        <property role="TrG5h" value="inputDigests" />
        <node concept="10Q1$e" id="4szZ9Zm1_3e" role="1tU5fm">
          <node concept="2D7PWU" id="4szZ9Zm1_3f" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="4szZ9Zm1_3g" role="jymVt" />
    <node concept="DJdLC" id="4szZ9Zm1_3h" role="jymVt">
      <property role="DRO8Q" value="Compute Mekle Root in a verifiable manner using left, path and direction as input" />
    </node>
    <node concept="DJdLC" id="4szZ9Zm1_3i" role="jymVt">
      <property role="DRO8Q" value="Return the computed Merkle Root" />
    </node>
    <node concept="3clFb_" id="4szZ9Zm1_3j" role="jymVt">
      <property role="1EzhhJ" value="false" />
      <property role="TrG5h" value="computeMerkleRoot" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="4szZ9Zm1_3k" role="3clF47">
        <node concept="3cpWs8" id="4szZ9Zm1_3l" role="3cqZAp">
          <node concept="3cpWsn" id="4szZ9Zm1_3m" role="3cpWs9">
            <property role="TrG5h" value="directionBits" />
            <node concept="10Q1$e" id="4szZ9Zm1_3n" role="1tU5fm">
              <node concept="1QD1ZQ" id="4szZ9Zm1_3o" role="10Q1$1" />
            </node>
            <node concept="2OqwBi" id="4szZ9Zm1_3p" role="33vP2m">
              <node concept="37vLTw" id="4szZ9Zm1_3q" role="2Oq$k0">
                <ref role="3cqZAo" node="4szZ9Zm1_2y" resolve="directionSelector" />
              </node>
              <node concept="1VPAEj" id="4szZ9Zm1_3r" role="2OqNvi" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="4szZ9Zm1_3s" role="3cqZAp">
          <node concept="3cpWsn" id="4szZ9Zm1_3t" role="3cpWs9">
            <property role="TrG5h" value="currentDigest" />
            <node concept="2D7PWU" id="4szZ9Zm1_3u" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="37vLTw" id="4szZ9Zm1_3v" role="33vP2m">
              <ref role="3cqZAo" node="4szZ9Zm1_4B" resolve="leaf" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="4szZ9Zm1_3w" role="3cqZAp">
          <node concept="3cpWsn" id="4szZ9Zm1_3x" role="3cpWs9">
            <property role="TrG5h" value="inputToNextHash" />
            <node concept="10Q1$e" id="4szZ9Zm1_3y" role="1tU5fm">
              <node concept="2D7PWU" id="4szZ9Zm1_3z" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="2ShNRf" id="4szZ9Zm1_3$" role="33vP2m">
              <node concept="3$_iS1" id="4szZ9Zm1_3_" role="2ShVmc">
                <node concept="3$GHV9" id="4szZ9Zm1_3A" role="3$GQph">
                  <node concept="3cmrfG" id="4szZ9Zm1_3B" role="3$I4v7">
                    <property role="3cmrfH" value="2" />
                  </node>
                </node>
                <node concept="2D7PWU" id="4szZ9Zm1_3C" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="4szZ9Zm1_3D" role="3cqZAp" />
        <node concept="1Dw8fO" id="4szZ9Zm1_3E" role="3cqZAp">
          <node concept="3clFbS" id="4szZ9Zm1_3F" role="2LFqv$">
            <node concept="1Dw8fO" id="4szZ9Zm1_3G" role="3cqZAp">
              <node concept="3clFbS" id="4szZ9Zm1_3H" role="2LFqv$">
                <node concept="3clFbJ" id="4szZ9Zm1_3I" role="3cqZAp">
                  <node concept="3clFbS" id="4szZ9Zm1_3J" role="3clFbx">
                    <node concept="3clFbF" id="4szZ9Zm1_3K" role="3cqZAp">
                      <node concept="37vLTI" id="4szZ9Zm1_3L" role="3clFbG">
                        <node concept="3K4zz7" id="4szZ9Zm1_3M" role="37vLTx">
                          <node concept="37vLTw" id="4szZ9Zm1_3N" role="3K4E3e">
                            <ref role="3cqZAo" node="4szZ9Zm1_3t" resolve="currentDigest" />
                          </node>
                          <node concept="AH0OO" id="4szZ9Zm1_3O" role="3K4GZi">
                            <node concept="37vLTw" id="4szZ9Zm1_3P" role="AHEQo">
                              <ref role="3cqZAo" node="4szZ9Zm1_4s" resolve="i" />
                            </node>
                            <node concept="37vLTw" id="4szZ9Zm1_3Q" role="AHHXb">
                              <ref role="3cqZAo" node="4szZ9Zm1_2A" resolve="digests" />
                            </node>
                          </node>
                          <node concept="2d3UOw" id="4szZ9Zm1_3R" role="3K4Cdx">
                            <node concept="3cmrfG" id="4szZ9Zm1_3S" role="3uHU7w">
                              <property role="3cmrfH" value="1" />
                            </node>
                            <node concept="37vLTw" id="4szZ9Zm1_3T" role="3uHU7B">
                              <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                            </node>
                          </node>
                        </node>
                        <node concept="AH0OO" id="4szZ9Zm1_3U" role="37vLTJ">
                          <node concept="37vLTw" id="4szZ9Zm1_3V" role="AHEQo">
                            <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                          </node>
                          <node concept="37vLTw" id="4szZ9Zm1_3W" role="AHHXb">
                            <ref role="3cqZAo" node="4szZ9Zm1_3x" resolve="inputToNextHash" />
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="AH0OO" id="4szZ9Zm1_3X" role="3clFbw">
                    <node concept="37vLTw" id="4szZ9Zm1_3Y" role="AHEQo">
                      <ref role="3cqZAo" node="4szZ9Zm1_4s" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="4szZ9Zm1_3Z" role="AHHXb">
                      <ref role="3cqZAo" node="4szZ9Zm1_3m" resolve="directionBits" />
                    </node>
                  </node>
                  <node concept="9aQIb" id="4szZ9Zm1_40" role="9aQIa">
                    <node concept="3clFbS" id="4szZ9Zm1_41" role="9aQI4">
                      <node concept="3clFbF" id="4szZ9Zm1_42" role="3cqZAp">
                        <node concept="37vLTI" id="4szZ9Zm1_43" role="3clFbG">
                          <node concept="3K4zz7" id="4szZ9Zm1_44" role="37vLTx">
                            <node concept="37vLTw" id="4szZ9Zm1_45" role="3K4E3e">
                              <ref role="3cqZAo" node="4szZ9Zm1_3t" resolve="currentDigest" />
                            </node>
                            <node concept="AH0OO" id="4szZ9Zm1_46" role="3K4GZi">
                              <node concept="37vLTw" id="4szZ9Zm1_47" role="AHEQo">
                                <ref role="3cqZAo" node="4szZ9Zm1_4s" resolve="i" />
                              </node>
                              <node concept="37vLTw" id="4szZ9Zm1_48" role="AHHXb">
                                <ref role="3cqZAo" node="4szZ9Zm1_2A" resolve="digests" />
                              </node>
                            </node>
                            <node concept="3eOVzh" id="4szZ9Zm1_49" role="3K4Cdx">
                              <node concept="37vLTw" id="4szZ9Zm1_4a" role="3uHU7B">
                                <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                              </node>
                              <node concept="3cmrfG" id="4szZ9Zm1_4b" role="3uHU7w">
                                <property role="3cmrfH" value="1" />
                              </node>
                            </node>
                          </node>
                          <node concept="AH0OO" id="4szZ9Zm1_4c" role="37vLTJ">
                            <node concept="37vLTw" id="4szZ9Zm1_4d" role="AHEQo">
                              <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                            </node>
                            <node concept="37vLTw" id="4szZ9Zm1_4e" role="AHHXb">
                              <ref role="3cqZAo" node="4szZ9Zm1_3x" resolve="inputToNextHash" />
                            </node>
                          </node>
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3cpWsn" id="4szZ9Zm1_4f" role="1Duv9x">
                <property role="TrG5h" value="j" />
                <node concept="10Oyi0" id="4szZ9Zm1_4g" role="1tU5fm" />
                <node concept="3cmrfG" id="4szZ9Zm1_4h" role="33vP2m">
                  <property role="3cmrfH" value="0" />
                </node>
              </node>
              <node concept="3eOVzh" id="4szZ9Zm1_4i" role="1Dwp0S">
                <node concept="37vLTw" id="4szZ9Zm1_4j" role="3uHU7B">
                  <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                </node>
                <node concept="3cmrfG" id="4szZ9Zm1_4k" role="3uHU7w">
                  <property role="3cmrfH" value="2" />
                </node>
              </node>
              <node concept="3uNrnE" id="4szZ9Zm1_4l" role="1Dwrff">
                <node concept="37vLTw" id="4szZ9Zm1_4m" role="2$L3a6">
                  <ref role="3cqZAo" node="4szZ9Zm1_4f" resolve="j" />
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="4szZ9Zm1_4n" role="3cqZAp">
              <node concept="37vLTI" id="4szZ9Zm1_4o" role="3clFbG">
                <node concept="2YIFZM" id="4szZ9Zm1_4p" role="37vLTx">
                  <ref role="37wK5l" to="7dh8:LEx6GtB4G1" resolve="poseidon_hash" />
                  <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
                  <node concept="37vLTw" id="4szZ9Zm1_4q" role="37wK5m">
                    <ref role="3cqZAo" node="4szZ9Zm1_3x" resolve="inputToNextHash" />
                  </node>
                </node>
                <node concept="37vLTw" id="4szZ9Zm1_4r" role="37vLTJ">
                  <ref role="3cqZAo" node="4szZ9Zm1_3t" resolve="currentDigest" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="4szZ9Zm1_4s" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="4szZ9Zm1_4t" role="1tU5fm" />
            <node concept="3cmrfG" id="4szZ9Zm1_4u" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="4szZ9Zm1_4v" role="1Dwp0S">
            <node concept="37vLTw" id="4szZ9Zm1_4w" role="3uHU7B">
              <ref role="3cqZAo" node="4szZ9Zm1_4s" resolve="i" />
            </node>
            <node concept="10M0yZ" id="4szZ9Zm1_4x" role="3uHU7w">
              <ref role="1PxDUh" node="1siqNxHAann" resolve="test_membership_proof_functions" />
              <ref role="3cqZAo" node="1siqNxHAhae" resolve="HEIGHT" />
            </node>
          </node>
          <node concept="3uNrnE" id="4szZ9Zm1_4y" role="1Dwrff">
            <node concept="37vLTw" id="4szZ9Zm1_4z" role="2$L3a6">
              <ref role="3cqZAo" node="4szZ9Zm1_4s" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3cpWs6" id="4szZ9Zm1_4$" role="3cqZAp">
          <node concept="37vLTw" id="4szZ9Zm1_4_" role="3cqZAk">
            <ref role="3cqZAo" node="4szZ9Zm1_3t" resolve="currentDigest" />
          </node>
        </node>
      </node>
      <node concept="2D7PWU" id="4szZ9Zm1_4A" role="3clF45">
        <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
      </node>
      <node concept="37vLTG" id="4szZ9Zm1_4B" role="3clF46">
        <property role="TrG5h" value="leaf" />
        <node concept="2D7PWU" id="4szZ9Zm1_4C" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="3Tm1VV" id="4szZ9Zm1_4D" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="4szZ9Zm1_4E" role="jymVt" />
    <node concept="3Tm1VV" id="4szZ9Zm1_4F" role="1B3o_S" />
  </node>
  <node concept="312cEu" id="1siqNxHAann">
    <property role="TrG5h" value="test_membership_proof_functions" />
    <node concept="2tJIrI" id="1siqNxHAano" role="jymVt" />
    <node concept="2tJIrI" id="1siqNxHAaOu" role="jymVt" />
    <node concept="Wx3nA" id="1siqNxHAhae" role="jymVt">
      <property role="2dlcS1" value="false" />
      <property role="2dld4O" value="false" />
      <property role="TrG5h" value="HEIGHT" />
      <property role="3TUv4t" value="false" />
      <node concept="3Tm1VV" id="1siqNxHAglK" role="1B3o_S" />
      <node concept="10Oyi0" id="1siqNxHAh9A" role="1tU5fm" />
      <node concept="3cmrfG" id="1siqNxHAhkP" role="33vP2m">
        <property role="3cmrfH" value="0" />
      </node>
    </node>
    <node concept="DJdLC" id="1siqNxHAanu" role="jymVt">
      <property role="DRO8Q" value="non_membership proof function" />
    </node>
    <node concept="DJdLC" id="1siqNxHAanv" role="jymVt">
      <property role="DRO8Q" value="It uses F_p[8] as its input_domain_name's format and takes MerkleAuthPath structure as its input format" />
    </node>
    <node concept="2YIFZL" id="1siqNxHAanw" role="jymVt">
      <property role="TrG5h" value="check_membership_path" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="1siqNxHAanx" role="3clF47">
        <node concept="3clFbH" id="1siqNxHAany" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAanz" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAan$" role="3SKWNk">
            <property role="3SKdUp" value="compute the hash of input_domain_name in the first layer" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAan_" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAanA" role="3SKWNk">
            <property role="3SKdUp" value="The needs an 8-input Poseidon Hash since domain name has a larger size" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAanB" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAanC" role="3cpWs9">
            <property role="TrG5h" value="leaf" />
            <node concept="2D7PWU" id="1siqNxHAanD" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="1siqNxHAanE" role="3cqZAp">
          <node concept="37vLTI" id="1siqNxHAanF" role="3clFbG">
            <node concept="2YIFZM" id="1siqNxHAanG" role="37vLTx">
              <ref role="37wK5l" to="7dh8:1y50vKi9QC$" resolve="poseidon_hash_8" />
              <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
              <node concept="37vLTw" id="1siqNxHAanH" role="37wK5m">
                <ref role="3cqZAo" node="1siqNxHAap2" resolve="input_domain_name" />
              </node>
            </node>
            <node concept="37vLTw" id="1siqNxHAanI" role="37vLTJ">
              <ref role="3cqZAo" node="1siqNxHAanC" resolve="leaf" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAanJ" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAanK" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAanL" role="3SKWNk">
            <property role="3SKdUp" value="compute the Merkle Root using left_left and right_left verifiably" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAanM" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAanN" role="3cpWs9">
            <property role="TrG5h" value="left_root" />
            <node concept="2D7PWU" id="1siqNxHAanO" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="1siqNxHAanP" role="33vP2m">
              <node concept="liA8E" id="1siqNxHAanQ" role="2OqNvi">
                <ref role="37wK5l" node="4szZ9Zm1_3j" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="1siqNxHAanR" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAap7" resolve="left_leaf" />
                </node>
              </node>
              <node concept="37vLTw" id="1siqNxHAanS" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAapb" resolve="authPath_left" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAanT" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAanU" role="3cpWs9">
            <property role="TrG5h" value="right_root" />
            <node concept="2D7PWU" id="1siqNxHAanV" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="1siqNxHAanW" role="33vP2m">
              <node concept="liA8E" id="1siqNxHAanX" role="2OqNvi">
                <ref role="37wK5l" node="4szZ9Zm1_3j" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="1siqNxHAanY" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAap9" resolve="right_leaf" />
                </node>
              </node>
              <node concept="37vLTw" id="1siqNxHAanZ" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAapd" resolve="authPath_right" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAao0" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAao1" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAao2" role="3SKWNk">
            <property role="3SKdUp" value="compare that roots are the same (public input root, roots computed from left and right)" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAao3" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAao4" role="3SKWNk">
            <property role="3SKdUp" value="It can prove that both left_leaf and right_leaf exist in Merkle Tree" />
          </node>
        </node>
        <node concept="3s6pcg" id="1siqNxHAao5" role="3cqZAp">
          <node concept="37vLTw" id="1siqNxHAao6" role="3s6pch">
            <ref role="3cqZAo" node="1siqNxHAanN" resolve="left_root" />
          </node>
          <node concept="37vLTw" id="1siqNxHAao7" role="3s6pci">
            <ref role="3cqZAo" node="1siqNxHAap5" resolve="root" />
          </node>
        </node>
        <node concept="3s6pcg" id="1siqNxHAao8" role="3cqZAp">
          <node concept="37vLTw" id="1siqNxHAao9" role="3s6pch">
            <ref role="3cqZAo" node="1siqNxHAanU" resolve="right_root" />
          </node>
          <node concept="37vLTw" id="1siqNxHAaoa" role="3s6pci">
            <ref role="3cqZAo" node="1siqNxHAap5" resolve="root" />
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaob" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAaoc" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaod" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf and right_leaf are adjacent!" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAaoe" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaof" role="3SKWNk">
            <property role="3SKdUp" value="The directionSelector is exactly the same as leaf's index position in first layer" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAaog" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAaoh" role="3cpWs9">
            <property role="TrG5h" value="one" />
            <node concept="3qc1$W" id="1siqNxHAaoi" role="1tU5fm">
              <property role="3qc1Xj" value="8" />
            </node>
            <node concept="3cmrfG" id="1siqNxHAaoj" role="33vP2m">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
        <node concept="3s6pcg" id="1siqNxHAaok" role="3cqZAp">
          <node concept="3cpWs3" id="1siqNxHAaol" role="3s6pch">
            <node concept="2OqwBi" id="1siqNxHAaom" role="3uHU7B">
              <node concept="37vLTw" id="1siqNxHAaon" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAapb" resolve="authPath_left" />
              </node>
              <node concept="2OwXpG" id="1siqNxHAaoo" role="2OqNvi">
                <ref role="2Oxat5" node="4szZ9Zm1_2y" resolve="directionSelector" />
              </node>
            </node>
            <node concept="37vLTw" id="1siqNxHAaop" role="3uHU7w">
              <ref role="3cqZAo" node="1siqNxHAaoh" resolve="one" />
            </node>
          </node>
          <node concept="2OqwBi" id="1siqNxHAaoq" role="3s6pci">
            <node concept="2OwXpG" id="1siqNxHAaor" role="2OqNvi">
              <ref role="2Oxat5" node="4szZ9Zm1_2y" resolve="directionSelector" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaos" role="2Oq$k0">
              <ref role="3cqZAo" node="1siqNxHAapd" resolve="authPath_right" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaot" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAaou" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaov" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf is indead smaller than right_leaf" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAaow" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaox" role="3SKWNk">
            <property role="3SKdUp" value="We can only compare uint, so we need such convert" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAaoy" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAaoz" role="3cpWs9">
            <property role="TrG5h" value="left_leaf_uint" />
            <node concept="3qc1$W" id="1siqNxHAao$" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="1siqNxHAao_" role="33vP2m">
              <node concept="3qc1$W" id="1siqNxHAaoA" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="1siqNxHAaoB" role="3Sueug">
                <ref role="3cqZAo" node="1siqNxHAap7" resolve="left_leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAaoC" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAaoD" role="3cpWs9">
            <property role="TrG5h" value="right_leaf_uint" />
            <node concept="3qc1$W" id="1siqNxHAaoE" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="1siqNxHAaoF" role="33vP2m">
              <node concept="3qc1$W" id="1siqNxHAaoG" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="1siqNxHAaoH" role="3Sueug">
                <ref role="3cqZAo" node="1siqNxHAap9" resolve="right_leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAaoI" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAaoJ" role="3cpWs9">
            <property role="TrG5h" value="leaf_uint" />
            <node concept="3qc1$W" id="1siqNxHAaoK" role="1tU5fm">
              <property role="3qc1Xj" value="256" />
            </node>
            <node concept="3SuevK" id="1siqNxHAaoL" role="33vP2m">
              <node concept="3qc1$W" id="1siqNxHAaoM" role="3SuevR">
                <property role="3qc1Xj" value="256" />
              </node>
              <node concept="37vLTw" id="1siqNxHAaoN" role="3Sueug">
                <ref role="3cqZAo" node="1siqNxHAanC" resolve="leaf" />
              </node>
            </node>
          </node>
        </node>
        <node concept="2DKZvD" id="1siqNxHAaoO" role="3cqZAp">
          <node concept="3eOVzh" id="1siqNxHAaoP" role="2DKX1R">
            <node concept="37vLTw" id="1siqNxHAaoQ" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAaoz" resolve="left_leaf_uint" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaoR" role="3uHU7w">
              <ref role="3cqZAo" node="1siqNxHAaoJ" resolve="leaf_uint" />
            </node>
          </node>
        </node>
        <node concept="2DKZvD" id="1siqNxHAaoS" role="3cqZAp">
          <node concept="3eOVzh" id="1siqNxHAaoT" role="2DKX1R">
            <node concept="37vLTw" id="1siqNxHAaoU" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAaoJ" resolve="leaf_uint" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaoV" role="3uHU7w">
              <ref role="3cqZAo" node="1siqNxHAaoD" resolve="right_leaf_uint" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaoW" role="3cqZAp" />
        <node concept="3cpWs6" id="1siqNxHAaoX" role="3cqZAp">
          <node concept="3SuevK" id="1siqNxHAaoY" role="3cqZAk">
            <node concept="3qc1$W" id="1siqNxHAaoZ" role="3SuevR">
              <property role="3qc1Xj" value="1" />
            </node>
            <node concept="3cmrfG" id="1siqNxHAap0" role="3Sueug">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3qc1$W" id="1siqNxHAap1" role="3clF45">
        <property role="3qc1Xj" value="1" />
      </node>
      <node concept="37vLTG" id="1siqNxHAap2" role="3clF46">
        <property role="TrG5h" value="input_domain_name" />
        <node concept="10Q1$e" id="1siqNxHAap3" role="1tU5fm">
          <node concept="2D7PWU" id="1siqNxHAap4" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAap5" role="3clF46">
        <property role="TrG5h" value="root" />
        <node concept="2D7PWU" id="1siqNxHAap6" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAap7" role="3clF46">
        <property role="TrG5h" value="left_leaf" />
        <node concept="2D7PWU" id="1siqNxHAap8" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAap9" role="3clF46">
        <property role="TrG5h" value="right_leaf" />
        <node concept="2D7PWU" id="1siqNxHAapa" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAapb" role="3clF46">
        <property role="TrG5h" value="authPath_left" />
        <node concept="3uibUv" id="1siqNxHAaXq" role="1tU5fm">
          <ref role="3uigEE" node="4szZ9Zm1_2v" resolve="Test_MerkleAuthPath" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAapd" role="3clF46">
        <property role="TrG5h" value="authPath_right" />
        <node concept="3uibUv" id="1siqNxHAaZU" role="1tU5fm">
          <ref role="3uigEE" node="4szZ9Zm1_2v" resolve="Test_MerkleAuthPath" />
        </node>
      </node>
      <node concept="3Tm1VV" id="1siqNxHAapf" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="1siqNxHAapg" role="jymVt" />
    <node concept="2tJIrI" id="1siqNxHAaph" role="jymVt" />
    <node concept="DJdLC" id="1siqNxHAapi" role="jymVt">
      <property role="DRO8Q" value="wildcard non_membership proof function" />
    </node>
    <node concept="DJdLC" id="1siqNxHAapj" role="jymVt">
      <property role="DRO8Q" value="It takes uint_8[255] as its input_domain_name's format and takes Merkle Path and directionSelector directly as its input format" />
    </node>
    <node concept="2YIFZL" id="1siqNxHAapk" role="jymVt">
      <property role="TrG5h" value="membershipProofChecks_M" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="1siqNxHAapl" role="3clF47">
        <node concept="3clFbF" id="1siqNxHAhpa" role="3cqZAp">
          <node concept="37vLTI" id="1siqNxHAh_d" role="3clFbG">
            <node concept="10M0yZ" id="1siqNxHAhE1" role="37vLTx">
              <ref role="1PxDUh" to="jgwk:3MwYnj0N6Oi" resolve="Test_HTTP_Merkle" />
              <ref role="3cqZAo" to="jgwk:3MwYnj2hZOl" resolve="HEIGHT" />
            </node>
            <node concept="37vLTw" id="1siqNxHAhp8" role="37vLTJ">
              <ref role="3cqZAo" node="1siqNxHAhae" resolve="HEIGHT" />
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAapm" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAapn" role="3SKWNk">
            <property role="3SKdUp" value="construct MerkAuthPath structure (compute root for left_path and right_path later)" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAapo" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAapp" role="3cpWs9">
            <property role="TrG5h" value="authPath_left" />
            <node concept="3uibUv" id="1siqNxHAb3J" role="1tU5fm">
              <ref role="3uigEE" node="4szZ9Zm1_2v" resolve="Test_MerkleAuthPath" />
            </node>
            <node concept="2ShNRf" id="1siqNxHAapr" role="33vP2m">
              <node concept="1pGfFk" id="1siqNxHAaps" role="2ShVmc">
                <ref role="37wK5l" node="4szZ9Zm1_2Z" resolve="Test_MerkleAuthPath" />
                <node concept="37vLTw" id="1siqNxHAapt" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAarw" resolve="direction" />
                </node>
                <node concept="37vLTw" id="1siqNxHAapu" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAart" resolve="authPath_array" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAapv" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAapw" role="3SKWNk">
            <property role="3SKdUp" value="convert the left and right domain name toF_p[8] to be accepted by Poseidon Hash" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAapx" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAapy" role="3cpWs9">
            <property role="TrG5h" value="leaf" />
            <node concept="2D7PWU" id="1siqNxHAapz" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAap$" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAap_" role="3cpWs9">
            <property role="TrG5h" value="leaf_url_fp" />
            <node concept="10Q1$e" id="1siqNxHAapA" role="1tU5fm">
              <node concept="2D7PWU" id="1siqNxHAapB" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="1rXfSq" id="1siqNxHAapC" role="33vP2m">
              <ref role="37wK5l" node="1siqNxHAar_" resolve="convert_8_to_Fp_python" />
              <node concept="37vLTw" id="1siqNxHAapD" role="37wK5m">
                <ref role="3cqZAo" node="1siqNxHAaro" resolve="leaf_url" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAapE" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAapF" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAapG" role="3SKWNk">
            <property role="3SKdUp" value="compute the hash of left and right domain name in the first layer" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAapH" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAapI" role="3SKWNk">
            <property role="3SKdUp" value="The needs an 8-input Poseidon Hash since domain name has a larger size" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAapJ" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAapK" role="3cpWs9">
            <property role="TrG5h" value="leaf_hash" />
            <node concept="2YIFZM" id="1siqNxHAapL" role="33vP2m">
              <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
              <ref role="37wK5l" to="7dh8:1y50vKi9QC$" resolve="poseidon_hash_8" />
              <node concept="37vLTw" id="1siqNxHAapM" role="37wK5m">
                <ref role="3cqZAo" node="1siqNxHAap_" resolve="leaf_url_fp" />
              </node>
            </node>
            <node concept="2D7PWU" id="1siqNxHAapN" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAapO" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAapP" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAapQ" role="3SKWNk">
            <property role="3SKdUp" value="compute the Merkle Root using left_left and right_left verifiably" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAapR" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAapS" role="3cpWs9">
            <property role="TrG5h" value="computed_root" />
            <node concept="2D7PWU" id="1siqNxHAapT" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="1siqNxHAapU" role="33vP2m">
              <node concept="37vLTw" id="1siqNxHAapV" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAapp" resolve="authPath_left" />
              </node>
              <node concept="liA8E" id="1siqNxHAapW" role="2OqNvi">
                <ref role="37wK5l" node="4szZ9Zm1_3j" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="1siqNxHAapX" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAapK" resolve="leaf_hash" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAapY" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAapZ" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaq0" role="3SKWNk">
            <property role="3SKdUp" value="compare that roots are the same (public input root, roots computed from left and right)" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAaq1" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaq2" role="3SKWNk">
            <property role="3SKdUp" value="It can prove that both left_leaf and right_leaf exist in Merkle Tree" />
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAaq3" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAaq4" role="8Wnug">
            <node concept="Xl_RD" id="1siqNxHAaq5" role="vCCx3">
              <property role="Xl_RC" value="root" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaq6" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAarm" resolve="root" />
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAaq7" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAaq8" role="8Wnug">
            <node concept="Xl_RD" id="1siqNxHAaq9" role="vCCx3">
              <property role="Xl_RC" value="computed root" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaqa" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAapS" resolve="computed_root" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaqb" role="3cqZAp" />
        <node concept="3s6pcg" id="1siqNxHAaqc" role="3cqZAp">
          <node concept="37vLTw" id="1siqNxHAaqd" role="3s6pch">
            <ref role="3cqZAo" node="1siqNxHAapS" resolve="computed_root" />
          </node>
          <node concept="37vLTw" id="1siqNxHAaqe" role="3s6pci">
            <ref role="3cqZAo" node="1siqNxHAarm" resolve="root" />
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaqf" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAaqg" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaqh" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf is indead smaller than right_leaf" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAaqi" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaqj" role="3SKWNk">
            <property role="3SKdUp" value="This firstly checks left_index and right_index for exact matching and then checks the next is smaller" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAaqk" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAaql" role="3SKWNk">
            <property role="3SKdUp" value="check out the notes for the algorithm" />
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAaqm" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAaqn" role="8Wnug">
            <node concept="37vLTw" id="1siqNxHAaqo" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAarr" resolve="leaf_length" />
            </node>
            <node concept="Xl_RD" id="1siqNxHAaqp" role="vCCx3">
              <property role="Xl_RC" value="index" />
            </node>
          </node>
        </node>
        <node concept="1Dw8fO" id="1siqNxHAaqq" role="3cqZAp">
          <node concept="3clFbS" id="1siqNxHAaqr" role="2LFqv$">
            <node concept="3clFbJ" id="1siqNxHAaqs" role="3cqZAp">
              <node concept="3clFbS" id="1siqNxHAaqt" role="3clFbx">
                <node concept="1X3_iC" id="1siqNxHAaqu" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="1siqNxHAaqv" role="8Wnug">
                    <node concept="AH0OO" id="1siqNxHAaqw" role="vCCWX">
                      <node concept="37vLTw" id="1siqNxHAaqx" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAaqy" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAaro" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="1siqNxHAaqz" role="vCCx3">
                      <property role="Xl_RC" value="leaf_url" />
                    </node>
                  </node>
                </node>
                <node concept="1X3_iC" id="1siqNxHAaq$" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="1siqNxHAaq_" role="8Wnug">
                    <node concept="AH0OO" id="1siqNxHAaqA" role="vCCWX">
                      <node concept="37vLTw" id="1siqNxHAaqB" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAaqC" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAarj" resolve="input_domain_name_wildcard" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="1siqNxHAaqD" role="vCCx3">
                      <property role="Xl_RC" value="input domain wildcard" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbH" id="1siqNxHAaqE" role="3cqZAp" />
                <node concept="3s6pcg" id="1siqNxHAaqF" role="3cqZAp">
                  <node concept="AH0OO" id="1siqNxHAaqG" role="3s6pch">
                    <node concept="37vLTw" id="1siqNxHAaqH" role="AHEQo">
                      <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAaqI" role="AHHXb">
                      <ref role="3cqZAo" node="1siqNxHAaro" resolve="leaf_url" />
                    </node>
                  </node>
                  <node concept="AH0OO" id="1siqNxHAaqJ" role="3s6pci">
                    <node concept="37vLTw" id="1siqNxHAaqK" role="AHEQo">
                      <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAaqL" role="AHHXb">
                      <ref role="3cqZAo" node="1siqNxHAarj" resolve="input_domain_name_wildcard" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eOVzh" id="1siqNxHAaqM" role="3clFbw">
                <node concept="3SuevK" id="1siqNxHAaqN" role="3uHU7B">
                  <node concept="3qc1$W" id="1siqNxHAaqO" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="37vLTw" id="1siqNxHAaqP" role="3Sueug">
                    <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                  </node>
                </node>
                <node concept="37vLTw" id="1siqNxHAaqQ" role="3uHU7w">
                  <ref role="3cqZAo" node="1siqNxHAarr" resolve="leaf_length" />
                </node>
              </node>
              <node concept="3eNFk2" id="1siqNxHAaqR" role="3eNLev">
                <node concept="2_lxnS" id="1siqNxHAaqS" role="3eO9$A">
                  <node concept="3SuevK" id="1siqNxHAaqT" role="3uHU7B">
                    <node concept="3qc1$W" id="1siqNxHAaqU" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAaqV" role="3Sueug">
                      <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                    </node>
                  </node>
                  <node concept="37vLTw" id="1siqNxHAaqW" role="3uHU7w">
                    <ref role="3cqZAo" node="1siqNxHAarr" resolve="leaf_length" />
                  </node>
                </node>
                <node concept="3clFbS" id="1siqNxHAaqX" role="3eOfB_">
                  <node concept="3s6pcg" id="1siqNxHAaqY" role="3cqZAp">
                    <node concept="AH0OO" id="1siqNxHAaqZ" role="3s6pch">
                      <node concept="37vLTw" id="1siqNxHAar0" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAar1" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAaro" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="3SuevK" id="1siqNxHAar2" role="3s6pci">
                      <node concept="3qc1$W" id="1siqNxHAar3" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="2nou5x" id="1siqNxHAar4" role="3Sueug">
                        <property role="2noCCI" value="00" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="1siqNxHAar5" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="1siqNxHAar6" role="1tU5fm" />
            <node concept="3cmrfG" id="1siqNxHAar7" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="1siqNxHAar8" role="1Dwp0S">
            <node concept="3cmrfG" id="1siqNxHAar9" role="3uHU7w">
              <property role="3cmrfH" value="255" />
            </node>
            <node concept="37vLTw" id="1siqNxHAara" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="1siqNxHAarb" role="1Dwrff">
            <node concept="37vLTw" id="1siqNxHAarc" role="2$L3a6">
              <ref role="3cqZAo" node="1siqNxHAar5" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAard" role="3cqZAp" />
        <node concept="3cpWs6" id="1siqNxHAare" role="3cqZAp">
          <node concept="3SuevK" id="1siqNxHAarf" role="3cqZAk">
            <node concept="3qc1$W" id="1siqNxHAarg" role="3SuevR">
              <property role="3qc1Xj" value="1" />
            </node>
            <node concept="3cmrfG" id="1siqNxHAarh" role="3Sueug">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3qc1$W" id="1siqNxHAari" role="3clF45">
        <property role="3qc1Xj" value="1" />
      </node>
      <node concept="37vLTG" id="1siqNxHAarj" role="3clF46">
        <property role="TrG5h" value="input_domain_name_wildcard" />
        <node concept="10Q1$e" id="1siqNxHAark" role="1tU5fm">
          <node concept="3qc1$W" id="1siqNxHAarl" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAarm" role="3clF46">
        <property role="TrG5h" value="root" />
        <node concept="2D7PWU" id="1siqNxHAarn" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAaro" role="3clF46">
        <property role="TrG5h" value="leaf_url" />
        <node concept="10Q1$e" id="1siqNxHAarp" role="1tU5fm">
          <node concept="3qc1$W" id="1siqNxHAarq" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAarr" role="3clF46">
        <property role="TrG5h" value="leaf_length" />
        <node concept="3qc1$W" id="1siqNxHAars" role="1tU5fm">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAart" role="3clF46">
        <property role="TrG5h" value="authPath_array" />
        <node concept="10Q1$e" id="1siqNxHAaru" role="1tU5fm">
          <node concept="2D7PWU" id="1siqNxHAarv" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAarw" role="3clF46">
        <property role="TrG5h" value="direction" />
        <node concept="3qc1$W" id="1siqNxHAarx" role="1tU5fm">
          <property role="3qc1Xj" value="64" />
        </node>
      </node>
      <node concept="3Tm1VV" id="1siqNxHAary" role="1B3o_S" />
    </node>
    <node concept="2YIFZL" id="1siqNxHAfSP" role="jymVt">
      <property role="TrG5h" value="membershipProofChecks_MT" />
      <property role="DiZV1" value="false" />
      <property role="od$2w" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="1siqNxHAfSQ" role="3clF47">
        <node concept="3clFbF" id="1siqNxHAhPG" role="3cqZAp">
          <node concept="37vLTI" id="1siqNxHAi2X" role="3clFbG">
            <node concept="10M0yZ" id="1siqNxHAi8d" role="37vLTx">
              <ref role="1PxDUh" to="jgwk:3MwYnj0N7dl" resolve="Test_HTTP_Merkle_Token" />
              <ref role="3cqZAo" to="jgwk:3MwYnj2hMHD" resolve="HEIGHT" />
            </node>
            <node concept="37vLTw" id="1siqNxHAhPE" role="37vLTJ">
              <ref role="3cqZAo" node="1siqNxHAhae" resolve="HEIGHT" />
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfSR" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfSS" role="3SKWNk">
            <property role="3SKdUp" value="construct MerkAuthPath structure (compute root for left_path and right_path later)" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAfST" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAfSU" role="3cpWs9">
            <property role="TrG5h" value="authPath_left" />
            <node concept="3uibUv" id="1siqNxHAfSV" role="1tU5fm">
              <ref role="3uigEE" node="4szZ9Zm1_2v" resolve="Test_MerkleAuthPath" />
            </node>
            <node concept="2ShNRf" id="1siqNxHAfSW" role="33vP2m">
              <node concept="1pGfFk" id="1siqNxHAfSX" role="2ShVmc">
                <ref role="37wK5l" node="4szZ9Zm1_2Z" resolve="Test_MerkleAuthPath" />
                <node concept="37vLTw" id="1siqNxHAfSY" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAfV1" resolve="direction" />
                </node>
                <node concept="37vLTw" id="1siqNxHAfSZ" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAfUY" resolve="authPath_array" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfT0" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfT1" role="3SKWNk">
            <property role="3SKdUp" value="convert the left and right domain name toF_p[8] to be accepted by Poseidon Hash" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAfT2" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAfT3" role="3cpWs9">
            <property role="TrG5h" value="leaf" />
            <node concept="2D7PWU" id="1siqNxHAfT4" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAfT5" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAfT6" role="3cpWs9">
            <property role="TrG5h" value="leaf_url_fp" />
            <node concept="10Q1$e" id="1siqNxHAfT7" role="1tU5fm">
              <node concept="2D7PWU" id="1siqNxHAfT8" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="1rXfSq" id="1siqNxHAfT9" role="33vP2m">
              <ref role="37wK5l" node="1siqNxHAar_" resolve="convert_8_to_Fp_python" />
              <node concept="37vLTw" id="1siqNxHAfTa" role="37wK5m">
                <ref role="3cqZAo" node="1siqNxHAfUT" resolve="leaf_url" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfTb" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAfTc" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTd" role="3SKWNk">
            <property role="3SKdUp" value="compute the hash of left and right domain name in the first layer" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfTe" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTf" role="3SKWNk">
            <property role="3SKdUp" value="The needs an 8-input Poseidon Hash since domain name has a larger size" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAfTg" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAfTh" role="3cpWs9">
            <property role="TrG5h" value="leaf_hash" />
            <node concept="2YIFZM" id="1siqNxHAfTi" role="33vP2m">
              <ref role="1Pybhc" to="7dh8:LEx6GtB3QS" resolve="PoseidonHash" />
              <ref role="37wK5l" to="7dh8:1y50vKi9QC$" resolve="poseidon_hash_8" />
              <node concept="37vLTw" id="1siqNxHAfTj" role="37wK5m">
                <ref role="3cqZAo" node="1siqNxHAfT6" resolve="leaf_url_fp" />
              </node>
            </node>
            <node concept="2D7PWU" id="1siqNxHAfTk" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfTl" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAfTm" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTn" role="3SKWNk">
            <property role="3SKdUp" value="compute the Merkle Root using left_left and right_left verifiably" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAfTo" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAfTp" role="3cpWs9">
            <property role="TrG5h" value="computed_root" />
            <node concept="2D7PWU" id="1siqNxHAfTq" role="1tU5fm">
              <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
            </node>
            <node concept="2OqwBi" id="1siqNxHAfTr" role="33vP2m">
              <node concept="37vLTw" id="1siqNxHAfTs" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAfSU" resolve="authPath_left" />
              </node>
              <node concept="liA8E" id="1siqNxHAfTt" role="2OqNvi">
                <ref role="37wK5l" node="4szZ9Zm1_3j" resolve="computeMerkleRoot" />
                <node concept="37vLTw" id="1siqNxHAfTu" role="37wK5m">
                  <ref role="3cqZAo" node="1siqNxHAfTh" resolve="leaf_hash" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfTv" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAfTw" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTx" role="3SKWNk">
            <property role="3SKdUp" value="compare that roots are the same (public input root, roots computed from left and right)" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfTy" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTz" role="3SKWNk">
            <property role="3SKdUp" value="It can prove that both left_leaf and right_leaf exist in Merkle Tree" />
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAfT$" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAfT_" role="8Wnug">
            <node concept="Xl_RD" id="1siqNxHAfTA" role="vCCx3">
              <property role="Xl_RC" value="root" />
            </node>
            <node concept="37vLTw" id="1siqNxHAfTB" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAfUR" resolve="root" />
            </node>
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAfTC" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAfTD" role="8Wnug">
            <node concept="Xl_RD" id="1siqNxHAfTE" role="vCCx3">
              <property role="Xl_RC" value="computed root" />
            </node>
            <node concept="37vLTw" id="1siqNxHAfTF" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAfTp" resolve="computed_root" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfTG" role="3cqZAp" />
        <node concept="3s6pcg" id="1siqNxHAfTH" role="3cqZAp">
          <node concept="37vLTw" id="1siqNxHAfTI" role="3s6pch">
            <ref role="3cqZAo" node="1siqNxHAfTp" resolve="computed_root" />
          </node>
          <node concept="37vLTw" id="1siqNxHAfTJ" role="3s6pci">
            <ref role="3cqZAo" node="1siqNxHAfUR" resolve="root" />
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfTK" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAfTL" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTM" role="3SKWNk">
            <property role="3SKdUp" value="Prove that left_leaf is indead smaller than right_leaf" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfTN" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTO" role="3SKWNk">
            <property role="3SKdUp" value="This firstly checks left_index and right_index for exact matching and then checks the next is smaller" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAfTP" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAfTQ" role="3SKWNk">
            <property role="3SKdUp" value="check out the notes for the algorithm" />
          </node>
        </node>
        <node concept="1X3_iC" id="1siqNxHAfTR" role="lGtFl">
          <property role="3V$3am" value="statement" />
          <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
          <node concept="vCCuG" id="1siqNxHAfTS" role="8Wnug">
            <node concept="37vLTw" id="1siqNxHAfTT" role="vCCWX">
              <ref role="3cqZAo" node="1siqNxHAfUW" resolve="leaf_length" />
            </node>
            <node concept="Xl_RD" id="1siqNxHAfTU" role="vCCx3">
              <property role="Xl_RC" value="index" />
            </node>
          </node>
        </node>
        <node concept="1Dw8fO" id="1siqNxHAfTV" role="3cqZAp">
          <node concept="3clFbS" id="1siqNxHAfTW" role="2LFqv$">
            <node concept="3clFbJ" id="1siqNxHAfTX" role="3cqZAp">
              <node concept="3clFbS" id="1siqNxHAfTY" role="3clFbx">
                <node concept="1X3_iC" id="1siqNxHAfTZ" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="1siqNxHAfU0" role="8Wnug">
                    <node concept="AH0OO" id="1siqNxHAfU1" role="vCCWX">
                      <node concept="37vLTw" id="1siqNxHAfU2" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAfU3" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAfUT" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="1siqNxHAfU4" role="vCCx3">
                      <property role="Xl_RC" value="leaf_url" />
                    </node>
                  </node>
                </node>
                <node concept="1X3_iC" id="1siqNxHAfU5" role="lGtFl">
                  <property role="3V$3am" value="statement" />
                  <property role="3V$3ak" value="f3061a53-9226-4cc5-a443-f952ceaf5816/1068580123136/1068581517665" />
                  <node concept="vCCuG" id="1siqNxHAfU6" role="8Wnug">
                    <node concept="AH0OO" id="1siqNxHAfU7" role="vCCWX">
                      <node concept="37vLTw" id="1siqNxHAfU8" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAfU9" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAfUO" resolve="input_domain_name_wildcard" />
                      </node>
                    </node>
                    <node concept="Xl_RD" id="1siqNxHAfUa" role="vCCx3">
                      <property role="Xl_RC" value="input domain wildcard" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbH" id="1siqNxHAfUb" role="3cqZAp" />
                <node concept="3s6pcg" id="1siqNxHAfUc" role="3cqZAp">
                  <node concept="AH0OO" id="1siqNxHAfUd" role="3s6pch">
                    <node concept="37vLTw" id="1siqNxHAfUe" role="AHEQo">
                      <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAfUf" role="AHHXb">
                      <ref role="3cqZAo" node="1siqNxHAfUT" resolve="leaf_url" />
                    </node>
                  </node>
                  <node concept="AH0OO" id="1siqNxHAfUg" role="3s6pci">
                    <node concept="37vLTw" id="1siqNxHAfUh" role="AHEQo">
                      <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAfUi" role="AHHXb">
                      <ref role="3cqZAo" node="1siqNxHAfUO" resolve="input_domain_name_wildcard" />
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3eOVzh" id="1siqNxHAfUj" role="3clFbw">
                <node concept="3SuevK" id="1siqNxHAfUk" role="3uHU7B">
                  <node concept="3qc1$W" id="1siqNxHAfUl" role="3SuevR">
                    <property role="3qc1Xj" value="8" />
                  </node>
                  <node concept="37vLTw" id="1siqNxHAfUm" role="3Sueug">
                    <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                  </node>
                </node>
                <node concept="37vLTw" id="1siqNxHAfUn" role="3uHU7w">
                  <ref role="3cqZAo" node="1siqNxHAfUW" resolve="leaf_length" />
                </node>
              </node>
              <node concept="3eNFk2" id="1siqNxHAfUo" role="3eNLev">
                <node concept="2_lxnS" id="1siqNxHAfUp" role="3eO9$A">
                  <node concept="3SuevK" id="1siqNxHAfUq" role="3uHU7B">
                    <node concept="3qc1$W" id="1siqNxHAfUr" role="3SuevR">
                      <property role="3qc1Xj" value="8" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAfUs" role="3Sueug">
                      <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                    </node>
                  </node>
                  <node concept="37vLTw" id="1siqNxHAfUt" role="3uHU7w">
                    <ref role="3cqZAo" node="1siqNxHAfUW" resolve="leaf_length" />
                  </node>
                </node>
                <node concept="3clFbS" id="1siqNxHAfUu" role="3eOfB_">
                  <node concept="3s6pcg" id="1siqNxHAfUv" role="3cqZAp">
                    <node concept="AH0OO" id="1siqNxHAfUw" role="3s6pch">
                      <node concept="37vLTw" id="1siqNxHAfUx" role="AHEQo">
                        <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAfUy" role="AHHXb">
                        <ref role="3cqZAo" node="1siqNxHAfUT" resolve="leaf_url" />
                      </node>
                    </node>
                    <node concept="3SuevK" id="1siqNxHAfUz" role="3s6pci">
                      <node concept="3qc1$W" id="1siqNxHAfU$" role="3SuevR">
                        <property role="3qc1Xj" value="8" />
                      </node>
                      <node concept="2nou5x" id="1siqNxHAfU_" role="3Sueug">
                        <property role="2noCCI" value="00" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="1siqNxHAfUA" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="1siqNxHAfUB" role="1tU5fm" />
            <node concept="3cmrfG" id="1siqNxHAfUC" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="1siqNxHAfUD" role="1Dwp0S">
            <node concept="3cmrfG" id="1siqNxHAfUE" role="3uHU7w">
              <property role="3cmrfH" value="200" />
            </node>
            <node concept="37vLTw" id="1siqNxHAfUF" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="1siqNxHAfUG" role="1Dwrff">
            <node concept="37vLTw" id="1siqNxHAfUH" role="2$L3a6">
              <ref role="3cqZAo" node="1siqNxHAfUA" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAfUI" role="3cqZAp" />
        <node concept="3cpWs6" id="1siqNxHAfUJ" role="3cqZAp">
          <node concept="3SuevK" id="1siqNxHAfUK" role="3cqZAk">
            <node concept="3qc1$W" id="1siqNxHAfUL" role="3SuevR">
              <property role="3qc1Xj" value="1" />
            </node>
            <node concept="3cmrfG" id="1siqNxHAfUM" role="3Sueug">
              <property role="3cmrfH" value="1" />
            </node>
          </node>
        </node>
      </node>
      <node concept="3qc1$W" id="1siqNxHAfUN" role="3clF45">
        <property role="3qc1Xj" value="1" />
      </node>
      <node concept="37vLTG" id="1siqNxHAfUO" role="3clF46">
        <property role="TrG5h" value="input_domain_name_wildcard" />
        <node concept="10Q1$e" id="1siqNxHAfUP" role="1tU5fm">
          <node concept="3qc1$W" id="1siqNxHAfUQ" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAfUR" role="3clF46">
        <property role="TrG5h" value="root" />
        <node concept="2D7PWU" id="1siqNxHAfUS" role="1tU5fm">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAfUT" role="3clF46">
        <property role="TrG5h" value="leaf_url" />
        <node concept="10Q1$e" id="1siqNxHAfUU" role="1tU5fm">
          <node concept="3qc1$W" id="1siqNxHAfUV" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAfUW" role="3clF46">
        <property role="TrG5h" value="leaf_length" />
        <node concept="3qc1$W" id="1siqNxHAfUX" role="1tU5fm">
          <property role="3qc1Xj" value="8" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAfUY" role="3clF46">
        <property role="TrG5h" value="authPath_array" />
        <node concept="10Q1$e" id="1siqNxHAfUZ" role="1tU5fm">
          <node concept="2D7PWU" id="1siqNxHAfV0" role="10Q1$1">
            <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
          </node>
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAfV1" role="3clF46">
        <property role="TrG5h" value="direction" />
        <node concept="3qc1$W" id="1siqNxHAfV2" role="1tU5fm">
          <property role="3qc1Xj" value="64" />
        </node>
      </node>
      <node concept="3Tm1VV" id="1siqNxHAfV3" role="1B3o_S" />
    </node>
    <node concept="2tJIrI" id="1siqNxHAfvF" role="jymVt" />
    <node concept="2tJIrI" id="1siqNxHAarz" role="jymVt" />
    <node concept="DJdLC" id="1siqNxHAar$" role="jymVt">
      <property role="DRO8Q" value="convert uint_8[] to F_p[8] -- the same as python script" />
    </node>
    <node concept="2YIFZL" id="1siqNxHAar_" role="jymVt">
      <property role="TrG5h" value="convert_8_to_Fp_python" />
      <property role="od$2w" value="false" />
      <property role="DiZV1" value="false" />
      <property role="2aFKle" value="false" />
      <node concept="3clFbS" id="1siqNxHAarA" role="3clF47">
        <node concept="3clFbH" id="1siqNxHAarB" role="3cqZAp" />
        <node concept="3cpWs8" id="1siqNxHAarC" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAarD" role="3cpWs9">
            <property role="TrG5h" value="Fp_array" />
            <node concept="10Q1$e" id="1siqNxHAarE" role="1tU5fm">
              <node concept="2D7PWU" id="1siqNxHAarF" role="10Q1$1">
                <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
              </node>
            </node>
            <node concept="2ShNRf" id="1siqNxHAarG" role="33vP2m">
              <node concept="3$_iS1" id="1siqNxHAarH" role="2ShVmc">
                <node concept="3$GHV9" id="1siqNxHAarI" role="3$GQph">
                  <node concept="3cmrfG" id="1siqNxHAarJ" role="3$I4v7">
                    <property role="3cmrfH" value="8" />
                  </node>
                </node>
                <node concept="2D7PWU" id="1siqNxHAarK" role="3$_nBY">
                  <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                </node>
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAarL" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAarM" role="3cpWs9">
            <property role="TrG5h" value="large_int" />
            <node concept="3qc1$W" id="1siqNxHAarN" role="1tU5fm">
              <property role="3qc1Xj" value="2040" />
            </node>
            <node concept="3SuevK" id="1siqNxHAarO" role="33vP2m">
              <node concept="3qc1$W" id="1siqNxHAarP" role="3SuevR">
                <property role="3qc1Xj" value="2040" />
              </node>
              <node concept="3cmrfG" id="1siqNxHAarQ" role="3Sueug">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAarR" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAarS" role="3cpWs9">
            <property role="TrG5h" value="tempt" />
            <node concept="3qc1$W" id="1siqNxHAarT" role="1tU5fm">
              <property role="3qc1Xj" value="2040" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAarU" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAarV" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAarW" role="3SKWNk">
            <property role="3SKdUp" value="concate uint_8[] to get uint_2040" />
          </node>
        </node>
        <node concept="1Dw8fO" id="1siqNxHAarX" role="3cqZAp">
          <node concept="3clFbS" id="1siqNxHAarY" role="2LFqv$">
            <node concept="3clFbF" id="1siqNxHAarZ" role="3cqZAp">
              <node concept="37vLTI" id="1siqNxHAas0" role="3clFbG">
                <node concept="3SuevK" id="1siqNxHAas1" role="37vLTx">
                  <node concept="3qc1$W" id="1siqNxHAas2" role="3SuevR">
                    <property role="3qc1Xj" value="2040" />
                  </node>
                  <node concept="1GRDU$" id="1siqNxHAas3" role="3Sueug">
                    <node concept="1eOMI4" id="1siqNxHAas4" role="3uHU7w">
                      <node concept="17qRlL" id="1siqNxHAas5" role="1eOMHV">
                        <node concept="37vLTw" id="1siqNxHAas6" role="3uHU7w">
                          <ref role="3cqZAo" node="1siqNxHAasl" resolve="i" />
                        </node>
                        <node concept="3cmrfG" id="1siqNxHAas7" role="3uHU7B">
                          <property role="3cmrfH" value="8" />
                        </node>
                      </node>
                    </node>
                    <node concept="3SuevK" id="1siqNxHAas8" role="3uHU7B">
                      <node concept="3qc1$W" id="1siqNxHAas9" role="3SuevR">
                        <property role="3qc1Xj" value="2040" />
                      </node>
                      <node concept="AH0OO" id="1siqNxHAasa" role="3Sueug">
                        <node concept="37vLTw" id="1siqNxHAasb" role="AHEQo">
                          <ref role="3cqZAo" node="1siqNxHAasl" resolve="i" />
                        </node>
                        <node concept="37vLTw" id="1siqNxHAasc" role="AHHXb">
                          <ref role="3cqZAo" node="1siqNxHAaus" resolve="byte_array" />
                        </node>
                      </node>
                    </node>
                  </node>
                </node>
                <node concept="37vLTw" id="1siqNxHAasd" role="37vLTJ">
                  <ref role="3cqZAo" node="1siqNxHAarS" resolve="tempt" />
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="1siqNxHAase" role="3cqZAp">
              <node concept="37vLTI" id="1siqNxHAasf" role="3clFbG">
                <node concept="3cpWs3" id="1siqNxHAasg" role="37vLTx">
                  <node concept="37vLTw" id="1siqNxHAash" role="3uHU7B">
                    <ref role="3cqZAo" node="1siqNxHAarM" resolve="large_int" />
                  </node>
                  <node concept="37vLTw" id="1siqNxHAasi" role="3uHU7w">
                    <ref role="3cqZAo" node="1siqNxHAarS" resolve="tempt" />
                  </node>
                </node>
                <node concept="37vLTw" id="1siqNxHAasj" role="37vLTJ">
                  <ref role="3cqZAo" node="1siqNxHAarM" resolve="large_int" />
                </node>
              </node>
            </node>
            <node concept="3clFbH" id="1siqNxHAask" role="3cqZAp" />
          </node>
          <node concept="3cpWsn" id="1siqNxHAasl" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="1siqNxHAasm" role="1tU5fm" />
            <node concept="3cmrfG" id="1siqNxHAasn" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="1siqNxHAaso" role="1Dwp0S">
            <node concept="3cmrfG" id="1siqNxHAasp" role="3uHU7w">
              <property role="3cmrfH" value="255" />
            </node>
            <node concept="37vLTw" id="1siqNxHAasq" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAasl" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="1siqNxHAasr" role="1Dwrff">
            <node concept="37vLTw" id="1siqNxHAass" role="2$L3a6">
              <ref role="3cqZAo" node="1siqNxHAasl" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAast" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAasu" role="3SKWNk">
            <property role="3SKdUp" value=" decompse uint_2040 into bit array" />
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAasv" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAasw" role="3cpWs9">
            <property role="TrG5h" value="bit_array" />
            <node concept="10Q1$e" id="1siqNxHAasx" role="1tU5fm">
              <node concept="1QD1ZQ" id="1siqNxHAasy" role="10Q1$1" />
            </node>
            <node concept="2ShNRf" id="1siqNxHAasz" role="33vP2m">
              <node concept="3$_iS1" id="1siqNxHAas$" role="2ShVmc">
                <node concept="3$GHV9" id="1siqNxHAas_" role="3$GQph">
                  <node concept="3cmrfG" id="1siqNxHAasA" role="3$I4v7">
                    <property role="3cmrfH" value="2040" />
                  </node>
                </node>
                <node concept="1QD1ZQ" id="1siqNxHAasB" role="3$_nBY" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3clFbF" id="1siqNxHAasC" role="3cqZAp">
          <node concept="37vLTI" id="1siqNxHAasD" role="3clFbG">
            <node concept="37vLTw" id="1siqNxHAasE" role="37vLTJ">
              <ref role="3cqZAo" node="1siqNxHAasw" resolve="bit_array" />
            </node>
            <node concept="2OqwBi" id="1siqNxHAasF" role="37vLTx">
              <node concept="37vLTw" id="1siqNxHAasG" role="2Oq$k0">
                <ref role="3cqZAo" node="1siqNxHAarM" resolve="large_int" />
              </node>
              <node concept="1VPAEj" id="1siqNxHAasH" role="2OqNvi" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAasI" role="3cqZAp" />
        <node concept="3clFbH" id="1siqNxHAasJ" role="3cqZAp" />
        <node concept="3cpWs8" id="1siqNxHAasK" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAasL" role="3cpWs9">
            <property role="TrG5h" value="Fp_buffer" />
            <node concept="3qc1$W" id="1siqNxHAasM" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
            <node concept="3SuevK" id="1siqNxHAasN" role="33vP2m">
              <node concept="3qc1$W" id="1siqNxHAasO" role="3SuevR">
                <property role="3qc1Xj" value="254" />
              </node>
              <node concept="3cmrfG" id="1siqNxHAasP" role="3Sueug">
                <property role="3cmrfH" value="0" />
              </node>
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAasQ" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAasR" role="3cpWs9">
            <property role="TrG5h" value="bit_tempt" />
            <node concept="3qc1$W" id="1siqNxHAasS" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
          </node>
        </node>
        <node concept="3cpWs8" id="1siqNxHAasT" role="3cqZAp">
          <node concept="3cpWsn" id="1siqNxHAasU" role="3cpWs9">
            <property role="TrG5h" value="Fp_tempt" />
            <node concept="3qc1$W" id="1siqNxHAasV" role="1tU5fm">
              <property role="3qc1Xj" value="254" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAasW" role="3cqZAp" />
        <node concept="3SKdUt" id="1siqNxHAasX" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAasY" role="3SKWNk">
            <property role="3SKdUp" value="recombine bit array into F_p array" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAasZ" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAat0" role="3SKWNk">
            <property role="3SKdUp" value="254 bit will convert to one F_p element" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAat1" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAat2" role="3SKWNk">
            <property role="3SKdUp" value="Note that it's not secure to convert 254bit directly since F_p is also 254 bit" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAat3" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAat4" role="3SKWNk">
            <property role="3SKdUp" value="Implementation needs to be tuned (revise it to 253bit or increase F_p)" />
          </node>
        </node>
        <node concept="3SKdUt" id="1siqNxHAat5" role="3cqZAp">
          <node concept="3SKdUq" id="1siqNxHAat6" role="3SKWNk">
            <property role="3SKdUp" value="It's enough to support 253bytes input since it's the largest size for input domain name" />
          </node>
        </node>
        <node concept="1Dw8fO" id="1siqNxHAat7" role="3cqZAp">
          <node concept="3clFbS" id="1siqNxHAat8" role="2LFqv$">
            <node concept="1Dw8fO" id="1siqNxHAat9" role="3cqZAp">
              <node concept="3clFbS" id="1siqNxHAata" role="2LFqv$">
                <node concept="3SKdUt" id="1siqNxHAatb" role="3cqZAp">
                  <node concept="3SKdUq" id="1siqNxHAatc" role="3SKWNk">
                    <property role="3SKdUp" value="get the bit" />
                  </node>
                </node>
                <node concept="3clFbF" id="1siqNxHAatd" role="3cqZAp">
                  <node concept="37vLTI" id="1siqNxHAate" role="3clFbG">
                    <node concept="3SuevK" id="1siqNxHAatf" role="37vLTx">
                      <node concept="3qc1$W" id="1siqNxHAatg" role="3SuevR">
                        <property role="3qc1Xj" value="254" />
                      </node>
                      <node concept="AH0OO" id="1siqNxHAath" role="3Sueug">
                        <node concept="3cpWs3" id="1siqNxHAati" role="AHEQo">
                          <node concept="37vLTw" id="1siqNxHAatj" role="3uHU7w">
                            <ref role="3cqZAo" node="1siqNxHAatR" resolve="j" />
                          </node>
                          <node concept="17qRlL" id="1siqNxHAatk" role="3uHU7B">
                            <node concept="37vLTw" id="1siqNxHAatl" role="3uHU7B">
                              <ref role="3cqZAo" node="1siqNxHAaud" resolve="i" />
                            </node>
                            <node concept="3cmrfG" id="1siqNxHAatm" role="3uHU7w">
                              <property role="3cmrfH" value="253" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="1siqNxHAatn" role="AHHXb">
                          <ref role="3cqZAo" node="1siqNxHAasw" resolve="bit_array" />
                        </node>
                      </node>
                    </node>
                    <node concept="37vLTw" id="1siqNxHAato" role="37vLTJ">
                      <ref role="3cqZAo" node="1siqNxHAasR" resolve="bit_tempt" />
                    </node>
                  </node>
                </node>
                <node concept="3SKdUt" id="1siqNxHAatp" role="3cqZAp">
                  <node concept="3SKdUq" id="1siqNxHAatq" role="3SKWNk">
                    <property role="3SKdUp" value="left shift this bit" />
                  </node>
                </node>
                <node concept="3clFbF" id="1siqNxHAatr" role="3cqZAp">
                  <node concept="37vLTI" id="1siqNxHAats" role="3clFbG">
                    <node concept="37vLTw" id="1siqNxHAatt" role="37vLTx">
                      <ref role="3cqZAo" node="1siqNxHAasR" resolve="bit_tempt" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAatu" role="37vLTJ">
                      <ref role="3cqZAo" node="1siqNxHAasU" resolve="Fp_tempt" />
                    </node>
                  </node>
                </node>
                <node concept="1Dw8fO" id="1siqNxHAatv" role="3cqZAp">
                  <node concept="3clFbS" id="1siqNxHAatw" role="2LFqv$">
                    <node concept="3clFbF" id="1siqNxHAatx" role="3cqZAp">
                      <node concept="37vLTI" id="1siqNxHAaty" role="3clFbG">
                        <node concept="17qRlL" id="1siqNxHAatz" role="37vLTx">
                          <node concept="37vLTw" id="1siqNxHAat$" role="3uHU7B">
                            <ref role="3cqZAo" node="1siqNxHAasU" resolve="Fp_tempt" />
                          </node>
                          <node concept="3SuevK" id="1siqNxHAat_" role="3uHU7w">
                            <node concept="3qc1$W" id="1siqNxHAatA" role="3SuevR">
                              <property role="3qc1Xj" value="2" />
                            </node>
                            <node concept="3cmrfG" id="1siqNxHAatB" role="3Sueug">
                              <property role="3cmrfH" value="2" />
                            </node>
                          </node>
                        </node>
                        <node concept="37vLTw" id="1siqNxHAatC" role="37vLTJ">
                          <ref role="3cqZAo" node="1siqNxHAasU" resolve="Fp_tempt" />
                        </node>
                      </node>
                    </node>
                  </node>
                  <node concept="3cpWsn" id="1siqNxHAatD" role="1Duv9x">
                    <property role="TrG5h" value="k" />
                    <node concept="10Oyi0" id="1siqNxHAatE" role="1tU5fm" />
                    <node concept="3cmrfG" id="1siqNxHAatF" role="33vP2m">
                      <property role="3cmrfH" value="0" />
                    </node>
                  </node>
                  <node concept="3eOVzh" id="1siqNxHAatG" role="1Dwp0S">
                    <node concept="37vLTw" id="1siqNxHAatH" role="3uHU7w">
                      <ref role="3cqZAo" node="1siqNxHAatR" resolve="j" />
                    </node>
                    <node concept="37vLTw" id="1siqNxHAatI" role="3uHU7B">
                      <ref role="3cqZAo" node="1siqNxHAatD" resolve="k" />
                    </node>
                  </node>
                  <node concept="3uNrnE" id="1siqNxHAatJ" role="1Dwrff">
                    <node concept="37vLTw" id="1siqNxHAatK" role="2$L3a6">
                      <ref role="3cqZAo" node="1siqNxHAatD" resolve="k" />
                    </node>
                  </node>
                </node>
                <node concept="3clFbF" id="1siqNxHAatL" role="3cqZAp">
                  <node concept="37vLTI" id="1siqNxHAatM" role="3clFbG">
                    <node concept="37vLTw" id="1siqNxHAatN" role="37vLTJ">
                      <ref role="3cqZAo" node="1siqNxHAasL" resolve="Fp_buffer" />
                    </node>
                    <node concept="3cpWs3" id="1siqNxHAatO" role="37vLTx">
                      <node concept="37vLTw" id="1siqNxHAatP" role="3uHU7w">
                        <ref role="3cqZAo" node="1siqNxHAasL" resolve="Fp_buffer" />
                      </node>
                      <node concept="37vLTw" id="1siqNxHAatQ" role="3uHU7B">
                        <ref role="3cqZAo" node="1siqNxHAasU" resolve="Fp_tempt" />
                      </node>
                    </node>
                  </node>
                </node>
              </node>
              <node concept="3cpWsn" id="1siqNxHAatR" role="1Duv9x">
                <property role="TrG5h" value="j" />
                <node concept="10Oyi0" id="1siqNxHAatS" role="1tU5fm" />
                <node concept="3cmrfG" id="1siqNxHAatT" role="33vP2m">
                  <property role="3cmrfH" value="0" />
                </node>
              </node>
              <node concept="3eOVzh" id="1siqNxHAatU" role="1Dwp0S">
                <node concept="3cmrfG" id="1siqNxHAatV" role="3uHU7w">
                  <property role="3cmrfH" value="253" />
                </node>
                <node concept="37vLTw" id="1siqNxHAatW" role="3uHU7B">
                  <ref role="3cqZAo" node="1siqNxHAatR" resolve="j" />
                </node>
              </node>
              <node concept="3uNrnE" id="1siqNxHAatX" role="1Dwrff">
                <node concept="37vLTw" id="1siqNxHAatY" role="2$L3a6">
                  <ref role="3cqZAo" node="1siqNxHAatR" resolve="j" />
                </node>
              </node>
            </node>
            <node concept="3SKdUt" id="1siqNxHAatZ" role="3cqZAp">
              <node concept="3SKdUq" id="1siqNxHAau0" role="3SKWNk">
                <property role="3SKdUp" value="turn uint254 to Fp" />
              </node>
            </node>
            <node concept="3clFbF" id="1siqNxHAau1" role="3cqZAp">
              <node concept="37vLTI" id="1siqNxHAau2" role="3clFbG">
                <node concept="AH0OO" id="1siqNxHAau3" role="37vLTJ">
                  <node concept="37vLTw" id="1siqNxHAau4" role="AHEQo">
                    <ref role="3cqZAo" node="1siqNxHAaud" resolve="i" />
                  </node>
                  <node concept="37vLTw" id="1siqNxHAau5" role="AHHXb">
                    <ref role="3cqZAo" node="1siqNxHAarD" resolve="Fp_array" />
                  </node>
                </node>
                <node concept="_hXgR" id="1siqNxHAau6" role="37vLTx">
                  <node concept="2D7PWU" id="1siqNxHAau7" role="_hXgQ">
                    <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
                  </node>
                  <node concept="37vLTw" id="1siqNxHAau8" role="_hXgL">
                    <ref role="3cqZAo" node="1siqNxHAasL" resolve="Fp_buffer" />
                  </node>
                </node>
              </node>
            </node>
            <node concept="3clFbF" id="1siqNxHAau9" role="3cqZAp">
              <node concept="37vLTI" id="1siqNxHAaua" role="3clFbG">
                <node concept="3cmrfG" id="1siqNxHAaub" role="37vLTx">
                  <property role="3cmrfH" value="0" />
                </node>
                <node concept="37vLTw" id="1siqNxHAauc" role="37vLTJ">
                  <ref role="3cqZAo" node="1siqNxHAasL" resolve="Fp_buffer" />
                </node>
              </node>
            </node>
          </node>
          <node concept="3cpWsn" id="1siqNxHAaud" role="1Duv9x">
            <property role="TrG5h" value="i" />
            <node concept="10Oyi0" id="1siqNxHAaue" role="1tU5fm" />
            <node concept="3cmrfG" id="1siqNxHAauf" role="33vP2m">
              <property role="3cmrfH" value="0" />
            </node>
          </node>
          <node concept="3eOVzh" id="1siqNxHAaug" role="1Dwp0S">
            <node concept="3cmrfG" id="1siqNxHAauh" role="3uHU7w">
              <property role="3cmrfH" value="8" />
            </node>
            <node concept="37vLTw" id="1siqNxHAaui" role="3uHU7B">
              <ref role="3cqZAo" node="1siqNxHAaud" resolve="i" />
            </node>
          </node>
          <node concept="3uNrnE" id="1siqNxHAauj" role="1Dwrff">
            <node concept="37vLTw" id="1siqNxHAauk" role="2$L3a6">
              <ref role="3cqZAo" node="1siqNxHAaud" resolve="i" />
            </node>
          </node>
        </node>
        <node concept="3clFbH" id="1siqNxHAaul" role="3cqZAp" />
        <node concept="3clFbH" id="1siqNxHAaum" role="3cqZAp" />
        <node concept="3cpWs6" id="1siqNxHAaun" role="3cqZAp">
          <node concept="37vLTw" id="1siqNxHAauo" role="3cqZAk">
            <ref role="3cqZAo" node="1siqNxHAarD" resolve="Fp_array" />
          </node>
        </node>
      </node>
      <node concept="3Tm1VV" id="1siqNxHAaup" role="1B3o_S" />
      <node concept="10Q1$e" id="1siqNxHAauq" role="3clF45">
        <node concept="2D7PWU" id="1siqNxHAaur" role="10Q1$1">
          <ref role="2D7PX4" to="tluv:6QM7J$VCJ7G" resolve="p" />
        </node>
      </node>
      <node concept="37vLTG" id="1siqNxHAaus" role="3clF46">
        <property role="TrG5h" value="byte_array" />
        <node concept="10Q1$e" id="1siqNxHAaut" role="1tU5fm">
          <node concept="3qc1$W" id="1siqNxHAauu" role="10Q1$1">
            <property role="3qc1Xj" value="8" />
          </node>
        </node>
      </node>
    </node>
    <node concept="2tJIrI" id="1siqNxHAauv" role="jymVt" />
    <node concept="2tJIrI" id="1siqNxHAauw" role="jymVt" />
    <node concept="2tJIrI" id="1siqNxHAaux" role="jymVt" />
    <node concept="3Tm1VV" id="1siqNxHAauy" role="1B3o_S" />
  </node>
</model>

