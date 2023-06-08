// code by jph
package ch.alpine.tensor.lie.bch;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/* package */ class BchSeriesA extends BchSeries {
  private static final Scalar P1_2 = RationalScalar.of(1, 2);
  private static final Scalar P1_12 = RationalScalar.of(1, 12);
  private static final Scalar N1_12 = RationalScalar.of(-1, 12);
  private static final Scalar N1_24 = RationalScalar.of(-1, 24);
  private static final Scalar N1_720 = RationalScalar.of(-1, 720);
  private static final Scalar N1_120 = RationalScalar.of(-1, 120);
  private static final Scalar N1_360 = RationalScalar.of(-1, 360);
  private static final Scalar P1_360 = RationalScalar.of(1, 360);
  private static final Scalar P1_120 = RationalScalar.of(1, 120);
  private static final Scalar P1_720 = RationalScalar.of(1, 720);
  private static final Scalar N1_1440 = RationalScalar.of(-1, 1440);
  private static final Scalar P1_240 = RationalScalar.of(1, 240);
  private static final Scalar P1_1440 = RationalScalar.of(1, 1440);
  private static final Scalar P1_30240 = RationalScalar.of(1, 30240);
  private static final Scalar P1_6720 = RationalScalar.of(1, 6720);
  private static final Scalar N1_15120 = RationalScalar.of(-1, 15120);
  private static final Scalar N1_10080 = RationalScalar.of(-1, 10080);
  private static final Scalar P1_10080 = RationalScalar.of(1, 10080);
  private static final Scalar N1_20160 = RationalScalar.of(-1, 20160);
  private static final Scalar P1_4032 = RationalScalar.of(1, 4032);
  private static final Scalar P1_1260 = RationalScalar.of(1, 1260);
  private static final Scalar P1_3360 = RationalScalar.of(1, 3360);
  private static final Scalar N1_7560 = RationalScalar.of(-1, 7560);
  private static final Scalar N1_1890 = RationalScalar.of(-1, 1890);
  private static final Scalar N1_672 = RationalScalar.of(-1, 672);
  private static final Scalar N1_3150 = RationalScalar.of(-1, 3150);
  private static final Scalar P1_6048 = RationalScalar.of(1, 6048);
  private static final Scalar P1_1680 = RationalScalar.of(1, 1680);
  private static final Scalar P1_4200 = RationalScalar.of(1, 4200);
  private static final Scalar N1_6048 = RationalScalar.of(-1, 6048);
  private static final Scalar N11_50400 = RationalScalar.of(-11, 50400);
  private static final Scalar N1_30240 = RationalScalar.of(-1, 30240);
  private static final Scalar P1_13440 = RationalScalar.of(1, 13440);
  private static final Scalar P1_12096 = RationalScalar.of(1, 12096);
  private static final Scalar N1_3360 = RationalScalar.of(-1, 3360);
  private static final Scalar N1_12096 = RationalScalar.of(-1, 12096);
  private static final Scalar P1_8064 = RationalScalar.of(1, 8064);
  private static final Scalar P1_2520 = RationalScalar.of(1, 2520);
  private static final Scalar P1_20160 = RationalScalar.of(1, 20160);
  private static final Scalar N1_3780 = RationalScalar.of(-1, 3780);
  private static final Scalar N1_1344 = RationalScalar.of(-1, 1344);
  private static final Scalar N1_6300 = RationalScalar.of(-1, 6300);
  private static final Scalar P1_8400 = RationalScalar.of(1, 8400);
  private static final Scalar N11_100800 = RationalScalar.of(-11, 100800);
  private static final Scalar N1_60480 = RationalScalar.of(-1, 60480);
  private static final Scalar N1_1209600 = RationalScalar.of(-1, 1209600);
  private static final Scalar P1_302400 = RationalScalar.of(1, 302400);
  private static final Scalar P1_100800 = RationalScalar.of(1, 100800);
  private static final Scalar N1_120960 = RationalScalar.of(-1, 120960);
  private static final Scalar N1_40320 = RationalScalar.of(-1, 40320);
  private static final Scalar N1_241920 = RationalScalar.of(-1, 241920);
  private static final Scalar N1_90720 = RationalScalar.of(-1, 90720);
  private static final Scalar N1_302400 = RationalScalar.of(-1, 302400);
  private static final Scalar P1_67200 = RationalScalar.of(1, 67200);
  private static final Scalar P1_40320 = RationalScalar.of(1, 40320);
  private static final Scalar P1_120960 = RationalScalar.of(1, 120960);
  private static final Scalar P1_50400 = RationalScalar.of(1, 50400);
  private static final Scalar P1_201600 = RationalScalar.of(1, 201600);
  private static final Scalar N1_86400 = RationalScalar.of(-1, 86400);
  private static final Scalar N1_25200 = RationalScalar.of(-1, 25200);
  private static final Scalar N1_50400 = RationalScalar.of(-1, 50400);
  private static final Scalar N1_151200 = RationalScalar.of(-1, 151200);
  private static final Scalar P1_22680 = RationalScalar.of(1, 22680);
  private static final Scalar P1_37800 = RationalScalar.of(1, 37800);
  private static final Scalar P1_3600 = RationalScalar.of(1, 3600);
  private static final Scalar P1_15120 = RationalScalar.of(1, 15120);
  private static final Scalar N1_12600 = RationalScalar.of(-1, 12600);
  private static final Scalar P1_88200 = RationalScalar.of(1, 88200);
  private static final Scalar N1_129600 = RationalScalar.of(-1, 129600);
  private static final Scalar N1_70560 = RationalScalar.of(-1, 70560);
  private static final Scalar P1_86400 = RationalScalar.of(1, 86400);
  private static final Scalar P1_52920 = RationalScalar.of(1, 52920);
  private static final Scalar P1_129600 = RationalScalar.of(1, 129600);
  private static final Scalar P17_2116800 = RationalScalar.of(17, 2116800);
  private static final Scalar P1_1209600 = RationalScalar.of(1, 1209600);
  private static final Scalar N1_259200 = RationalScalar.of(-1, 259200);
  private static final Scalar P11_604800 = RationalScalar.of(11, 604800);
  private static final Scalar P1_172800 = RationalScalar.of(1, 172800);
  private static final Scalar N1_69120 = RationalScalar.of(-1, 69120);
  private static final Scalar N1_80640 = RationalScalar.of(-1, 80640);
  private static final Scalar N1_362880 = RationalScalar.of(-1, 362880);
  private static final Scalar N1_172800 = RationalScalar.of(-1, 172800);
  private static final Scalar P1_45360 = RationalScalar.of(1, 45360);
  private static final Scalar P1_75600 = RationalScalar.of(1, 75600);
  private static final Scalar P1_362880 = RationalScalar.of(1, 362880);
  private static final Scalar P1_259200 = RationalScalar.of(1, 259200);
  private static final Scalar P1_80640 = RationalScalar.of(1, 80640);
  private static final Scalar N1_100800 = RationalScalar.of(-1, 100800);
  private static final Scalar N1_483840 = RationalScalar.of(-1, 483840);
  private static final Scalar N1_604800 = RationalScalar.of(-1, 604800);
  private static final Scalar P1_604800 = RationalScalar.of(1, 604800);
  private static final Scalar P1_7200 = RationalScalar.of(1, 7200);
  private static final Scalar P1_176400 = RationalScalar.of(1, 176400);
  private static final Scalar N1_141120 = RationalScalar.of(-1, 141120);
  private static final Scalar P1_105840 = RationalScalar.of(1, 105840);
  private static final Scalar P17_4233600 = RationalScalar.of(17, 4233600);
  private static final Scalar P1_2419200 = RationalScalar.of(1, 2419200);

  public BchSeriesA(Tensor ad) {
    super(ad);
  }

  @Override
  public Tensor series(Tensor x, Tensor y) {
    Tensor adx = ad.dot(x);
    Tensor ady = ad.dot(y);
    // d = 1
    Tensor t1 = x.add(y);
    // d = 2
    Tensor xy = adx.dot(y);
    Tensor t2_0 = xy.multiply(P1_2);
    Tensor t2 = t2_0;
    // d = 3
    Tensor xxy = adx.dot(xy);
    Tensor t3_0 = xxy.multiply(P1_12);
    Tensor yxy = ady.dot(xy);
    Tensor t3_1 = yxy.multiply(N1_12);
    Tensor t3 = t3_0.add(t3_1);
    // d = 4
    Tensor xyxy = adx.dot(yxy);
    Tensor t4_0 = xyxy.multiply(N1_24);
    Tensor t4 = t4_0;
    // d = 5
    Tensor xxxy = adx.dot(xxy);
    Tensor xxxxy = adx.dot(xxxy);
    Tensor t5_0 = xxxxy.multiply(N1_720);
    Tensor xxyxy = adx.dot(xyxy);
    Tensor t5_1 = xxyxy.multiply(N1_120);
    Tensor yyxy = ady.dot(yxy);
    Tensor xyyxy = adx.dot(yyxy);
    Tensor t5_2 = xyyxy.multiply(N1_360);
    Tensor yxxxy = ady.dot(xxxy);
    Tensor t5_3 = yxxxy.multiply(P1_360);
    Tensor yxyxy = ady.dot(xyxy);
    Tensor t5_4 = yxyxy.multiply(P1_120);
    Tensor yyyxy = ady.dot(yyxy);
    Tensor t5_5 = yyyxy.multiply(P1_720);
    Tensor t5 = t5_0.add(t5_1).add(t5_2).add(t5_3).add(t5_4).add(t5_5);
    // d = 6
    Tensor xxxyxy = adx.dot(xxyxy);
    Tensor t6_0 = xxxyxy.multiply(N1_1440);
    Tensor xxyyxy = adx.dot(xyyxy);
    Tensor t6_1 = xxyyxy.multiply(N1_720);
    Tensor xyxxxy = adx.dot(yxxxy);
    Tensor t6_2 = xyxxxy.multiply(P1_720);
    Tensor xyxyxy = adx.dot(yxyxy);
    Tensor t6_3 = xyxyxy.multiply(P1_240);
    Tensor xyyyxy = adx.dot(yyyxy);
    Tensor t6_4 = xyyyxy.multiply(P1_1440);
    Tensor t6 = t6_0.add(t6_1).add(t6_2).add(t6_3).add(t6_4);
    // d = 7
    Tensor xxxxxy = adx.dot(xxxxy);
    Tensor xxxxxxy = adx.dot(xxxxxy);
    Tensor t7_0 = xxxxxxy.multiply(P1_30240);
    Tensor xxxxyxy = adx.dot(xxxyxy);
    Tensor t7_1 = xxxxyxy.multiply(P1_6720);
    Tensor xxxyyxy = adx.dot(xxyyxy);
    Tensor t7_2 = xxxyyxy.multiply(N1_15120);
    Tensor xxyxxxy = adx.dot(xyxxxy);
    Tensor t7_3 = xxyxxxy.multiply(N1_10080);
    Tensor xxyxyxy = adx.dot(xyxyxy);
    Tensor t7_4 = xxyxyxy.multiply(P1_10080);
    Tensor xxyyyxy = adx.dot(xyyyxy);
    Tensor t7_5 = xxyyyxy.multiply(N1_20160);
    Tensor yxxxxy = ady.dot(xxxxy);
    Tensor xyxxxxy = adx.dot(yxxxxy);
    Tensor t7_6 = xyxxxxy.multiply(P1_4032);
    Tensor yxxyxy = ady.dot(xxyxy);
    Tensor xyxxyxy = adx.dot(yxxyxy);
    Tensor t7_7 = xyxxyxy.multiply(P1_1260);
    Tensor yxyyxy = ady.dot(xyyxy);
    Tensor xyxyyxy = adx.dot(yxyyxy);
    Tensor t7_8 = xyxyyxy.multiply(P1_3360);
    Tensor yyxxxy = ady.dot(yxxxy);
    Tensor xyyxxxy = adx.dot(yyxxxy);
    Tensor t7_9 = xyyxxxy.multiply(N1_15120);
    Tensor yyxyxy = ady.dot(yxyxy);
    Tensor xyyxyxy = adx.dot(yyxyxy);
    Tensor t7_10 = xyyxyxy.multiply(P1_4032);
    Tensor yyyyxy = ady.dot(yyyxy);
    Tensor xyyyyxy = adx.dot(yyyyxy);
    Tensor t7_11 = xyyyyxy.multiply(P1_10080);
    Tensor yxxxxxy = ady.dot(xxxxxy);
    Tensor t7_12 = yxxxxxy.multiply(N1_10080);
    Tensor yxxxyxy = ady.dot(xxxyxy);
    Tensor t7_13 = yxxxyxy.multiply(N1_7560);
    Tensor yxxyyxy = ady.dot(xxyyxy);
    Tensor t7_14 = yxxyyxy.multiply(P1_3360);
    Tensor yxyxxxy = ady.dot(xyxxxy);
    Tensor t7_15 = yxyxxxy.multiply(N1_1890);
    Tensor yxyxyxy = ady.dot(xyxyxy);
    Tensor t7_16 = yxyxyxy.multiply(N1_672);
    Tensor yxyyyxy = ady.dot(xyyyxy);
    Tensor t7_17 = yxyyyxy.multiply(N1_3150);
    Tensor yyxxxxy = ady.dot(yxxxxy);
    Tensor t7_18 = yyxxxxy.multiply(P1_6048);
    Tensor yyxxyxy = ady.dot(yxxyxy);
    Tensor t7_19 = yyxxyxy.multiply(P1_1680);
    Tensor yyxyyxy = ady.dot(yxyyxy);
    Tensor t7_20 = yyxyyxy.multiply(P1_4200);
    Tensor yyyxxxy = ady.dot(yyxxxy);
    Tensor t7_21 = yyyxxxy.multiply(N1_6048);
    Tensor yyyxyxy = ady.dot(yyxyxy);
    Tensor t7_22 = yyyxyxy.multiply(N11_50400);
    Tensor yyyyyxy = ady.dot(yyyyxy);
    Tensor t7_23 = yyyyyxy.multiply(N1_30240);
    Tensor t7 = t7_0.add(t7_1).add(t7_2).add(t7_3).add(t7_4).add(t7_5).add(t7_6).add(t7_7).add(t7_8).add(t7_9).add(t7_10).add(t7_11).add(t7_12).add(t7_13)
        .add(t7_14).add(t7_15).add(t7_16).add(t7_17).add(t7_18).add(t7_19).add(t7_20).add(t7_21).add(t7_22).add(t7_23);
    // d = 8
    Tensor xxxxxyxy = adx.dot(xxxxyxy);
    Tensor t8_0 = xxxxxyxy.multiply(P1_13440);
    Tensor xxxxyyxy = adx.dot(xxxyyxy);
    Tensor t8_1 = xxxxyyxy.multiply(P1_12096);
    Tensor xxxyxxxy = adx.dot(xxyxxxy);
    Tensor t8_2 = xxxyxxxy.multiply(N1_6048);
    Tensor xxxyxyxy = adx.dot(xxyxyxy);
    Tensor t8_3 = xxxyxyxy.multiply(N1_3360);
    Tensor xxxyyyxy = adx.dot(xxyyyxy);
    Tensor t8_4 = xxxyyyxy.multiply(N1_12096);
    Tensor xxyxxxxy = adx.dot(xyxxxxy);
    Tensor t8_5 = xxyxxxxy.multiply(P1_8064);
    Tensor xxyxxyxy = adx.dot(xyxxyxy);
    Tensor t8_6 = xxyxxyxy.multiply(P1_2520);
    Tensor xxyxyyxy = adx.dot(xyxyyxy);
    Tensor t8_7 = xxyxyyxy.multiply(P1_6720);
    Tensor xxyyxxxy = adx.dot(xyyxxxy);
    Tensor t8_8 = xxyyxxxy.multiply(N1_30240);
    Tensor xxyyxyxy = adx.dot(xyyxyxy);
    Tensor t8_9 = xxyyxyxy.multiply(P1_8064);
    Tensor xxyyyyxy = adx.dot(xyyyyxy);
    Tensor t8_10 = xxyyyyxy.multiply(P1_20160);
    Tensor xyxxxxxy = adx.dot(yxxxxxy);
    Tensor t8_11 = xyxxxxxy.multiply(N1_20160);
    Tensor xyxxxyxy = adx.dot(yxxxyxy);
    Tensor t8_12 = xyxxxyxy.multiply(N1_15120);
    Tensor xyxxyyxy = adx.dot(yxxyyxy);
    Tensor t8_13 = xyxxyyxy.multiply(P1_6720);
    Tensor xyxyxxxy = adx.dot(yxyxxxy);
    Tensor t8_14 = xyxyxxxy.multiply(N1_3780);
    Tensor xyxyxyxy = adx.dot(yxyxyxy);
    Tensor t8_15 = xyxyxyxy.multiply(N1_1344);
    Tensor xyxyyyxy = adx.dot(yxyyyxy);
    Tensor t8_16 = xyxyyyxy.multiply(N1_6300);
    Tensor xyyxxxxy = adx.dot(yyxxxxy);
    Tensor t8_17 = xyyxxxxy.multiply(P1_12096);
    Tensor xyyxxyxy = adx.dot(yyxxyxy);
    Tensor t8_18 = xyyxxyxy.multiply(P1_3360);
    Tensor xyyxyyxy = adx.dot(yyxyyxy);
    Tensor t8_19 = xyyxyyxy.multiply(P1_8400);
    Tensor xyyyxxxy = adx.dot(yyyxxxy);
    Tensor t8_20 = xyyyxxxy.multiply(N1_12096);
    Tensor xyyyxyxy = adx.dot(yyyxyxy);
    Tensor t8_21 = xyyyxyxy.multiply(N11_100800);
    Tensor xyyyyyxy = adx.dot(yyyyyxy);
    Tensor t8_22 = xyyyyyxy.multiply(N1_60480);
    Tensor t8 = t8_0.add(t8_1).add(t8_2).add(t8_3).add(t8_4).add(t8_5).add(t8_6).add(t8_7).add(t8_8).add(t8_9).add(t8_10).add(t8_11).add(t8_12).add(t8_13)
        .add(t8_14).add(t8_15).add(t8_16).add(t8_17).add(t8_18).add(t8_19).add(t8_20).add(t8_21).add(t8_22);
    // d = 9
    Tensor xxxxxxxy = adx.dot(xxxxxxy);
    Tensor xxxxxxxxy = adx.dot(xxxxxxxy);
    Tensor t9_0 = xxxxxxxxy.multiply(N1_1209600);
    Tensor xxxxxxyxy = adx.dot(xxxxxyxy);
    Tensor t9_1 = xxxxxxyxy.multiply(P1_302400);
    Tensor xxxxxyyxy = adx.dot(xxxxyyxy);
    Tensor t9_2 = xxxxxyyxy.multiply(P1_100800);
    Tensor xxxxyxxxy = adx.dot(xxxyxxxy);
    Tensor t9_3 = xxxxyxxxy.multiply(N1_120960);
    Tensor xxxxyxyxy = adx.dot(xxxyxyxy);
    Tensor t9_4 = xxxxyxyxy.multiply(N1_40320);
    Tensor xxxxyyyxy = adx.dot(xxxyyyxy);
    Tensor t9_5 = xxxxyyyxy.multiply(N1_241920);
    Tensor xxxyxxxxy = adx.dot(xxyxxxxy);
    Tensor t9_6 = xxxyxxxxy.multiply(N1_120960);
    Tensor xxxyyxxxy = adx.dot(xxyyxxxy);
    Tensor t9_7 = xxxyyxxxy.multiply(N1_90720);
    Tensor xxxyyxyxy = adx.dot(xxyyxyxy);
    Tensor t9_8 = xxxyyxyxy.multiply(N1_120960);
    Tensor xxxyyyyxy = adx.dot(xxyyyyxy);
    Tensor t9_9 = xxxyyyyxy.multiply(N1_302400);
    Tensor xxyxxxxxy = adx.dot(xyxxxxxy);
    Tensor t9_10 = xxyxxxxxy.multiply(P1_67200);
    Tensor xxyxxxyxy = adx.dot(xyxxxyxy);
    Tensor t9_11 = xxyxxxyxy.multiply(P1_30240);
    Tensor xxyxxyyxy = adx.dot(xyxxyyxy);
    Tensor t9_12 = xxyxxyyxy.multiply(P1_40320);
    Tensor xxyxyxyxy = adx.dot(xyxyxyxy);
    Tensor t9_13 = xxyxyxyxy.multiply(N1_40320);
    Tensor xxyyxxxxy = adx.dot(xyyxxxxy);
    Tensor t9_14 = xxyyxxxxy.multiply(P1_120960);
    Tensor xxyyxxyxy = adx.dot(xyyxxyxy);
    Tensor t9_15 = xxyyxxyxy.multiply(P1_20160);
    Tensor xxyyxyyxy = adx.dot(xyyxyyxy);
    Tensor t9_16 = xxyyxyyxy.multiply(P1_50400);
    Tensor xxyyyxxxy = adx.dot(xyyyxxxy);
    Tensor t9_17 = xxyyyxxxy.multiply(N1_120960);
    Tensor xxyyyxyxy = adx.dot(xyyyxyxy);
    Tensor t9_18 = xxyyyxyxy.multiply(P1_201600);
    Tensor xxyyyyyxy = adx.dot(xyyyyyxy);
    Tensor t9_19 = xxyyyyyxy.multiply(P1_201600);
    Tensor yxxxxxxy = ady.dot(xxxxxxy);
    Tensor xyxxxxxxy = adx.dot(yxxxxxxy);
    Tensor t9_20 = xyxxxxxxy.multiply(N1_86400);
    Tensor yxxxxyxy = ady.dot(xxxxyxy);
    Tensor xyxxxxyxy = adx.dot(yxxxxyxy);
    Tensor t9_21 = xyxxxxyxy.multiply(N1_30240);
    Tensor yxxyxyxy = ady.dot(xxyxyxy);
    Tensor xyxxyxyxy = adx.dot(yxxyxyxy);
    Tensor t9_22 = xyxxyxyxy.multiply(N1_40320);
    Tensor yxyxxxxy = ady.dot(xyxxxxy);
    Tensor xyxyxxxxy = adx.dot(yxyxxxxy);
    Tensor t9_23 = xyxyxxxxy.multiply(N1_30240);
    Tensor yxyxxyxy = ady.dot(xyxxyxy);
    Tensor xyxyxxyxy = adx.dot(yxyxxyxy);
    Tensor t9_24 = xyxyxxyxy.multiply(N1_10080);
    Tensor yxyxyyxy = ady.dot(xyxyyxy);
    Tensor xyxyxyyxy = adx.dot(yxyxyyxy);
    Tensor t9_25 = xyxyxyyxy.multiply(N1_25200);
    Tensor yxyyxyxy = ady.dot(xyyxyxy);
    Tensor xyxyyxyxy = adx.dot(yxyyxyxy);
    Tensor t9_26 = xyxyyxyxy.multiply(N1_25200);
    Tensor yxyyyyxy = ady.dot(xyyyyxy);
    Tensor xyxyyyyxy = adx.dot(yxyyyyxy);
    Tensor t9_27 = xyxyyyyxy.multiply(N1_60480);
    Tensor yyxxxxxy = ady.dot(yxxxxxy);
    Tensor xyyxxxxxy = adx.dot(yyxxxxxy);
    Tensor t9_28 = xyyxxxxxy.multiply(P1_100800);
    Tensor yyxxxyxy = ady.dot(yxxxyxy);
    Tensor xyyxxxyxy = adx.dot(yyxxxyxy);
    Tensor t9_29 = xyyxxxyxy.multiply(P1_40320);
    Tensor yyxxyyxy = ady.dot(yxxyyxy);
    Tensor xyyxxyyxy = adx.dot(yyxxyyxy);
    Tensor t9_30 = xyyxxyyxy.multiply(P1_50400);
    Tensor yyxyxyxy = ady.dot(yxyxyxy);
    Tensor xyyxyxyxy = adx.dot(yyxyxyxy);
    Tensor t9_31 = xyyxyxyxy.multiply(N1_50400);
    Tensor yyyxxxxy = ady.dot(yyxxxxy);
    Tensor xyyyxxxxy = adx.dot(yyyxxxxy);
    Tensor t9_32 = xyyyxxxxy.multiply(N1_241920);
    Tensor yyyyxxxy = ady.dot(yyyxxxy);
    Tensor xyyyyxxxy = adx.dot(yyyyxxxy);
    Tensor t9_33 = xyyyyxxxy.multiply(N1_302400);
    Tensor yyyyxyxy = ady.dot(yyyxyxy);
    Tensor xyyyyxyxy = adx.dot(yyyyxyxy);
    Tensor t9_34 = xyyyyxyxy.multiply(N1_86400);
    Tensor yyyyyyxy = ady.dot(yyyyyxy);
    Tensor xyyyyyyxy = adx.dot(yyyyyyxy);
    Tensor t9_35 = xyyyyyyxy.multiply(N1_302400);
    Tensor yxxxxxxxy = ady.dot(xxxxxxxy);
    Tensor t9_36 = yxxxxxxxy.multiply(P1_302400);
    Tensor yxxxxxyxy = ady.dot(xxxxxyxy);
    Tensor t9_37 = yxxxxxyxy.multiply(N1_151200);
    Tensor yxxxxyyxy = ady.dot(xxxxyyxy);
    Tensor t9_38 = yxxxxyyxy.multiply(N1_40320);
    Tensor yxxxyxxxy = ady.dot(xxxyxxxy);
    Tensor t9_39 = yxxxyxxxy.multiply(P1_22680);
    Tensor yxxxyxyxy = ady.dot(xxxyxyxy);
    Tensor t9_40 = yxxxyxyxy.multiply(P1_10080);
    Tensor yxxxyyyxy = ady.dot(xxxyyyxy);
    Tensor t9_41 = yxxxyyyxy.multiply(P1_37800);
    Tensor yxxyxxxxy = ady.dot(xxyxxxxy);
    Tensor t9_42 = yxxyxxxxy.multiply(N1_30240);
    Tensor yxxyxxyxy = ady.dot(xxyxxyxy);
    Tensor t9_43 = yxxyxxyxy.multiply(N1_10080);
    Tensor yxxyxyyxy = ady.dot(xxyxyyxy);
    Tensor t9_44 = yxxyxyyxy.multiply(N1_25200);
    Tensor yxxyyxyxy = ady.dot(xxyyxyxy);
    Tensor t9_45 = yxxyyxyxy.multiply(N1_25200);
    Tensor yxxyyyyxy = ady.dot(xxyyyyxy);
    Tensor t9_46 = yxxyyyyxy.multiply(N1_60480);
    Tensor yxyxxxxxy = ady.dot(xyxxxxxy);
    Tensor t9_47 = yxyxxxxxy.multiply(P1_37800);
    Tensor yxyxxxyxy = ady.dot(xyxxxyxy);
    Tensor t9_48 = yxyxxxyxy.multiply(P1_20160);
    Tensor yxyxxyyxy = ady.dot(xyxxyyxy);
    Tensor t9_49 = yxyxxyyxy.multiply(N1_25200);
    Tensor yxyxyxxxy = ady.dot(xyxyxxxy);
    Tensor t9_50 = yxyxyxxxy.multiply(P1_10080);
    Tensor yxyxyxyxy = ady.dot(xyxyxyxy);
    Tensor t9_51 = yxyxyxyxy.multiply(P1_3600);
    Tensor yxyxyyyxy = ady.dot(xyxyyyxy);
    Tensor t9_52 = yxyxyyyxy.multiply(P1_15120);
    Tensor yxyyxxxxy = ady.dot(xyyxxxxy);
    Tensor t9_53 = yxyyxxxxy.multiply(N1_40320);
    Tensor yxyyxxyxy = ady.dot(xyyxxyxy);
    Tensor t9_54 = yxyyxxyxy.multiply(N1_12600);
    Tensor yxyyxyyxy = ady.dot(xyyxyyxy);
    Tensor t9_55 = yxyyxyyxy.multiply(N1_30240);
    Tensor yxyyyxxxy = ady.dot(xyyyxxxy);
    Tensor t9_56 = yxyyyxxxy.multiply(P1_37800);
    Tensor yxyyyxyxy = ady.dot(xyyyxyxy);
    Tensor t9_57 = yxyyyxyxy.multiply(P1_20160);
    Tensor yxyyyyyxy = ady.dot(xyyyyyxy);
    Tensor t9_58 = yxyyyyyxy.multiply(P1_88200);
    Tensor yyxxxxxxy = ady.dot(yxxxxxxy);
    Tensor t9_59 = yyxxxxxxy.multiply(N1_129600);
    Tensor yyxxxxyxy = ady.dot(yxxxxyxy);
    Tensor t9_60 = yyxxxxyxy.multiply(N1_40320);
    Tensor yyxxyxyxy = ady.dot(yxxyxyxy);
    Tensor t9_61 = yyxxyxyxy.multiply(N1_50400);
    Tensor yyxyxxxxy = ady.dot(yxyxxxxy);
    Tensor t9_62 = yyxyxxxxy.multiply(N1_40320);
    Tensor yyxyxxyxy = ady.dot(yxyxxyxy);
    Tensor t9_63 = yyxyxxyxy.multiply(N1_12600);
    Tensor yyxyxyyxy = ady.dot(yxyxyyxy);
    Tensor t9_64 = yyxyxyyxy.multiply(N1_30240);
    Tensor yyxyyxyxy = ady.dot(yxyyxyxy);
    Tensor t9_65 = yyxyyxyxy.multiply(N1_30240);
    Tensor yyxyyyyxy = ady.dot(yxyyyyxy);
    Tensor t9_66 = yyxyyyyxy.multiply(N1_70560);
    Tensor yyyxxxxxy = ady.dot(yyxxxxxy);
    Tensor t9_67 = yyyxxxxxy.multiply(P1_86400);
    Tensor yyyxxxyxy = ady.dot(yyxxxyxy);
    Tensor t9_68 = yyyxxxyxy.multiply(P1_37800);
    Tensor yyxyxxxy = ady.dot(yxyxxxy);
    Tensor yyyxyxxxy = ady.dot(yyxyxxxy);
    Tensor t9_69 = yyyxyxxxy.multiply(P1_37800);
    Tensor yyyxyxyxy = ady.dot(yyxyxyxy);
    Tensor t9_70 = yyyxyxyxy.multiply(P1_15120);
    Tensor yyxyyyxy = ady.dot(yxyyyxy);
    Tensor yyyxyyyxy = ady.dot(yyxyyyxy);
    Tensor t9_71 = yyyxyyyxy.multiply(P1_52920);
    Tensor yyyyxxxxy = ady.dot(yyyxxxxy);
    Tensor t9_72 = yyyyxxxxy.multiply(N1_86400);
    Tensor yyyxxyxy = ady.dot(yyxxyxy);
    Tensor yyyyxxyxy = ady.dot(yyyxxyxy);
    Tensor t9_73 = yyyyxxyxy.multiply(N1_30240);
    Tensor yyyxyyxy = ady.dot(yyxyyxy);
    Tensor yyyyxyyxy = ady.dot(yyyxyyxy);
    Tensor t9_74 = yyyyxyyxy.multiply(N1_70560);
    Tensor yyyyyxxxy = ady.dot(yyyyxxxy);
    Tensor t9_75 = yyyyyxxxy.multiply(P1_129600);
    Tensor yyyyyxyxy = ady.dot(yyyyxyxy);
    Tensor t9_76 = yyyyyxyxy.multiply(P17_2116800);
    Tensor yyyyyyyxy = ady.dot(yyyyyyxy);
    Tensor t9_77 = yyyyyyyxy.multiply(P1_1209600);
    Tensor t9 = t9_0.add(t9_1).add(t9_2).add(t9_3).add(t9_4).add(t9_5).add(t9_6).add(t9_7).add(t9_8).add(t9_9).add(t9_10).add(t9_11).add(t9_12).add(t9_13)
        .add(t9_14).add(t9_15).add(t9_16).add(t9_17).add(t9_18).add(t9_19).add(t9_20).add(t9_21).add(t9_22).add(t9_23).add(t9_24).add(t9_25).add(t9_26)
        .add(t9_27).add(t9_28).add(t9_29).add(t9_30).add(t9_31).add(t9_32).add(t9_33).add(t9_34).add(t9_35).add(t9_36).add(t9_37).add(t9_38).add(t9_39)
        .add(t9_40).add(t9_41).add(t9_42).add(t9_43).add(t9_44).add(t9_45).add(t9_46).add(t9_47).add(t9_48).add(t9_49).add(t9_50).add(t9_51).add(t9_52)
        .add(t9_53).add(t9_54).add(t9_55).add(t9_56).add(t9_57).add(t9_58).add(t9_59).add(t9_60).add(t9_61).add(t9_62).add(t9_63).add(t9_64).add(t9_65)
        .add(t9_66).add(t9_67).add(t9_68).add(t9_69).add(t9_70).add(t9_71).add(t9_72).add(t9_73).add(t9_74).add(t9_75).add(t9_76).add(t9_77);
    // d = 10
    Tensor xxxxxxxyxy = adx.dot(xxxxxxyxy);
    Tensor t10_0 = xxxxxxxyxy.multiply(N1_241920);
    Tensor xxxxxxyyxy = adx.dot(xxxxxyyxy);
    Tensor t10_1 = xxxxxxyyxy.multiply(N1_259200);
    Tensor xxxxxyxxxy = adx.dot(xxxxyxxxy);
    Tensor t10_2 = xxxxxyxxxy.multiply(P1_86400);
    Tensor xxxxxyxyxy = adx.dot(xxxxyxyxy);
    Tensor t10_3 = xxxxxyxyxy.multiply(P11_604800);
    Tensor xxxxxyyyxy = adx.dot(xxxxyyyxy);
    Tensor t10_4 = xxxxxyyyxy.multiply(P1_172800);
    Tensor xxxxyxxxxy = adx.dot(xxxyxxxxy);
    Tensor t10_5 = xxxxyxxxxy.multiply(N1_69120);
    Tensor xxxyxxyxy = adx.dot(xxyxxyxy);
    Tensor xxxxyxxyxy = adx.dot(xxxyxxyxy);
    Tensor t10_6 = xxxxyxxyxy.multiply(N1_30240);
    Tensor xxxyxyyxy = adx.dot(xxyxyyxy);
    Tensor xxxxyxyyxy = adx.dot(xxxyxyyxy);
    Tensor t10_7 = xxxxyxyyxy.multiply(N1_80640);
    Tensor xxxxyyxxxy = adx.dot(xxxyyxxxy);
    Tensor t10_8 = xxxxyyxxxy.multiply(N1_362880);
    Tensor xxxxyyxyxy = adx.dot(xxxyyxyxy);
    Tensor t10_9 = xxxxyyxyxy.multiply(N1_69120);
    Tensor xxxxyyyyxy = adx.dot(xxxyyyyxy);
    Tensor t10_10 = xxxxyyyyxy.multiply(N1_172800);
    Tensor xxxyxxxxxy = adx.dot(xxyxxxxxy);
    Tensor t10_11 = xxxyxxxxxy.multiply(P1_86400);
    Tensor xxxyxxxyxy = adx.dot(xxyxxxyxy);
    Tensor t10_12 = xxxyxxxyxy.multiply(P1_45360);
    Tensor xxyxyxxxy = adx.dot(xyxyxxxy);
    Tensor xxxyxyxxxy = adx.dot(xxyxyxxxy);
    Tensor t10_13 = xxxyxyxxxy.multiply(P1_45360);
    Tensor xxxyxyxyxy = adx.dot(xxyxyxyxy);
    Tensor t10_14 = xxxyxyxyxy.multiply(P1_20160);
    Tensor xxyxyyyxy = adx.dot(xyxyyyxy);
    Tensor xxxyxyyyxy = adx.dot(xxyxyyyxy);
    Tensor t10_15 = xxxyxyyyxy.multiply(P1_75600);
    Tensor xxxyyxxxxy = adx.dot(xxyyxxxxy);
    Tensor t10_16 = xxxyyxxxxy.multiply(N1_362880);
    Tensor xxxyyyxxxy = adx.dot(xxyyyxxxy);
    Tensor t10_17 = xxxyyyxxxy.multiply(P1_362880);
    Tensor xxxyyyxyxy = adx.dot(xxyyyxyxy);
    Tensor t10_18 = xxxyyyxyxy.multiply(P1_86400);
    Tensor xxxyyyyyxy = adx.dot(xxyyyyyxy);
    Tensor t10_19 = xxxyyyyyxy.multiply(P1_259200);
    Tensor xxyxxxxxxy = adx.dot(xyxxxxxxy);
    Tensor t10_20 = xxyxxxxxxy.multiply(N1_172800);
    Tensor xxyxxxxyxy = adx.dot(xyxxxxyxy);
    Tensor t10_21 = xxyxxxxyxy.multiply(N1_60480);
    Tensor xxyxxyxyxy = adx.dot(xyxxyxyxy);
    Tensor t10_22 = xxyxxyxyxy.multiply(N1_80640);
    Tensor xxyxyxxxxy = adx.dot(xyxyxxxxy);
    Tensor t10_23 = xxyxyxxxxy.multiply(N1_60480);
    Tensor xxyxyxxyxy = adx.dot(xyxyxxyxy);
    Tensor t10_24 = xxyxyxxyxy.multiply(N1_20160);
    Tensor xxyxyxyyxy = adx.dot(xyxyxyyxy);
    Tensor t10_25 = xxyxyxyyxy.multiply(N1_50400);
    Tensor xxyxyyxyxy = adx.dot(xyxyyxyxy);
    Tensor t10_26 = xxyxyyxyxy.multiply(N1_50400);
    Tensor xxyxyyyyxy = adx.dot(xyxyyyyxy);
    Tensor t10_27 = xxyxyyyyxy.multiply(N1_120960);
    Tensor xxyyxxxxxy = adx.dot(xyyxxxxxy);
    Tensor t10_28 = xxyyxxxxxy.multiply(P1_201600);
    Tensor xxyyxxxyxy = adx.dot(xyyxxxyxy);
    Tensor t10_29 = xxyyxxxyxy.multiply(P1_80640);
    Tensor xxyyxxyyxy = adx.dot(xyyxxyyxy);
    Tensor t10_30 = xxyyxxyyxy.multiply(P1_100800);
    Tensor xxyyxyxyxy = adx.dot(xyyxyxyxy);
    Tensor t10_31 = xxyyxyxyxy.multiply(N1_100800);
    Tensor xxyyyxxxxy = adx.dot(xyyyxxxxy);
    Tensor t10_32 = xxyyyxxxxy.multiply(N1_483840);
    Tensor xxyyyyxxxy = adx.dot(xyyyyxxxy);
    Tensor t10_33 = xxyyyyxxxy.multiply(N1_604800);
    Tensor xxyyyyxyxy = adx.dot(xyyyyxyxy);
    Tensor t10_34 = xxyyyyxyxy.multiply(N1_172800);
    Tensor xxyyyyyyxy = adx.dot(xyyyyyyxy);
    Tensor t10_35 = xxyyyyyyxy.multiply(N1_604800);
    Tensor xyxxxxxxxy = adx.dot(yxxxxxxxy);
    Tensor t10_36 = xyxxxxxxxy.multiply(P1_604800);
    Tensor xyxxxxxyxy = adx.dot(yxxxxxyxy);
    Tensor t10_37 = xyxxxxxyxy.multiply(N1_302400);
    Tensor xyxxxxyyxy = adx.dot(yxxxxyyxy);
    Tensor t10_38 = xyxxxxyyxy.multiply(N1_80640);
    Tensor xyxxxyxxxy = adx.dot(yxxxyxxxy);
    Tensor t10_39 = xyxxxyxxxy.multiply(P1_45360);
    Tensor xyxxxyxyxy = adx.dot(yxxxyxyxy);
    Tensor t10_40 = xyxxxyxyxy.multiply(P1_20160);
    Tensor xyxxxyyyxy = adx.dot(yxxxyyyxy);
    Tensor t10_41 = xyxxxyyyxy.multiply(P1_75600);
    Tensor xyxxyxxxxy = adx.dot(yxxyxxxxy);
    Tensor t10_42 = xyxxyxxxxy.multiply(N1_60480);
    Tensor xyxxyxxyxy = adx.dot(yxxyxxyxy);
    Tensor t10_43 = xyxxyxxyxy.multiply(N1_20160);
    Tensor xyxxyxyyxy = adx.dot(yxxyxyyxy);
    Tensor t10_44 = xyxxyxyyxy.multiply(N1_50400);
    Tensor xyxxyyxyxy = adx.dot(yxxyyxyxy);
    Tensor t10_45 = xyxxyyxyxy.multiply(N1_50400);
    Tensor xyxxyyyyxy = adx.dot(yxxyyyyxy);
    Tensor t10_46 = xyxxyyyyxy.multiply(N1_120960);
    Tensor xyxyxxxxxy = adx.dot(yxyxxxxxy);
    Tensor t10_47 = xyxyxxxxxy.multiply(P1_75600);
    Tensor xyxyxxxyxy = adx.dot(yxyxxxyxy);
    Tensor t10_48 = xyxyxxxyxy.multiply(P1_40320);
    Tensor xyxyxxyyxy = adx.dot(yxyxxyyxy);
    Tensor t10_49 = xyxyxxyyxy.multiply(N1_50400);
    Tensor xyxyxyxxxy = adx.dot(yxyxyxxxy);
    Tensor t10_50 = xyxyxyxxxy.multiply(P1_20160);
    Tensor xyxyxyxyxy = adx.dot(yxyxyxyxy);
    Tensor t10_51 = xyxyxyxyxy.multiply(P1_7200);
    Tensor xyxyxyyyxy = adx.dot(yxyxyyyxy);
    Tensor t10_52 = xyxyxyyyxy.multiply(P1_30240);
    Tensor xyxyyxxxxy = adx.dot(yxyyxxxxy);
    Tensor t10_53 = xyxyyxxxxy.multiply(N1_80640);
    Tensor xyxyyxxyxy = adx.dot(yxyyxxyxy);
    Tensor t10_54 = xyxyyxxyxy.multiply(N1_25200);
    Tensor xyxyyxyyxy = adx.dot(yxyyxyyxy);
    Tensor t10_55 = xyxyyxyyxy.multiply(N1_60480);
    Tensor xyxyyyxxxy = adx.dot(yxyyyxxxy);
    Tensor t10_56 = xyxyyyxxxy.multiply(P1_75600);
    Tensor xyxyyyxyxy = adx.dot(yxyyyxyxy);
    Tensor t10_57 = xyxyyyxyxy.multiply(P1_40320);
    Tensor xyxyyyyyxy = adx.dot(yxyyyyyxy);
    Tensor t10_58 = xyxyyyyyxy.multiply(P1_176400);
    Tensor xyyxxxxxxy = adx.dot(yyxxxxxxy);
    Tensor t10_59 = xyyxxxxxxy.multiply(N1_259200);
    Tensor xyyxxxxyxy = adx.dot(yyxxxxyxy);
    Tensor t10_60 = xyyxxxxyxy.multiply(N1_80640);
    Tensor xyyxxyxyxy = adx.dot(yyxxyxyxy);
    Tensor t10_61 = xyyxxyxyxy.multiply(N1_100800);
    Tensor xyyxyxxxxy = adx.dot(yyxyxxxxy);
    Tensor t10_62 = xyyxyxxxxy.multiply(N1_80640);
    Tensor xyyxyxxyxy = adx.dot(yyxyxxyxy);
    Tensor t10_63 = xyyxyxxyxy.multiply(N1_25200);
    Tensor xyyxyxyyxy = adx.dot(yyxyxyyxy);
    Tensor t10_64 = xyyxyxyyxy.multiply(N1_60480);
    Tensor xyyxyyxyxy = adx.dot(yyxyyxyxy);
    Tensor t10_65 = xyyxyyxyxy.multiply(N1_60480);
    Tensor xyyxyyyyxy = adx.dot(yyxyyyyxy);
    Tensor t10_66 = xyyxyyyyxy.multiply(N1_141120);
    Tensor xyyyxxxxxy = adx.dot(yyyxxxxxy);
    Tensor t10_67 = xyyyxxxxxy.multiply(P1_172800);
    Tensor xyyyxxxyxy = adx.dot(yyyxxxyxy);
    Tensor t10_68 = xyyyxxxyxy.multiply(P1_75600);
    Tensor xyyyxyxxxy = adx.dot(yyyxyxxxy);
    Tensor t10_69 = xyyyxyxxxy.multiply(P1_75600);
    Tensor xyyyxyxyxy = adx.dot(yyyxyxyxy);
    Tensor t10_70 = xyyyxyxyxy.multiply(P1_30240);
    Tensor xyyyxyyyxy = adx.dot(yyyxyyyxy);
    Tensor t10_71 = xyyyxyyyxy.multiply(P1_105840);
    Tensor xyyyyxxxxy = adx.dot(yyyyxxxxy);
    Tensor t10_72 = xyyyyxxxxy.multiply(N1_172800);
    Tensor xyyyyxxyxy = adx.dot(yyyyxxyxy);
    Tensor t10_73 = xyyyyxxyxy.multiply(N1_60480);
    Tensor xyyyyxyyxy = adx.dot(yyyyxyyxy);
    Tensor t10_74 = xyyyyxyyxy.multiply(N1_141120);
    Tensor xyyyyyxxxy = adx.dot(yyyyyxxxy);
    Tensor t10_75 = xyyyyyxxxy.multiply(P1_259200);
    Tensor xyyyyyxyxy = adx.dot(yyyyyxyxy);
    Tensor t10_76 = xyyyyyxyxy.multiply(P17_4233600);
    Tensor xyyyyyyyxy = adx.dot(yyyyyyyxy);
    Tensor t10_77 = xyyyyyyyxy.multiply(P1_2419200);
    Tensor t10 = t10_0.add(t10_1).add(t10_2).add(t10_3).add(t10_4).add(t10_5).add(t10_6).add(t10_7).add(t10_8).add(t10_9).add(t10_10).add(t10_11).add(t10_12)
        .add(t10_13).add(t10_14).add(t10_15).add(t10_16).add(t10_17).add(t10_18).add(t10_19).add(t10_20).add(t10_21).add(t10_22).add(t10_23).add(t10_24)
        .add(t10_25).add(t10_26).add(t10_27).add(t10_28).add(t10_29).add(t10_30).add(t10_31).add(t10_32).add(t10_33).add(t10_34).add(t10_35).add(t10_36)
        .add(t10_37).add(t10_38).add(t10_39).add(t10_40).add(t10_41).add(t10_42).add(t10_43).add(t10_44).add(t10_45).add(t10_46).add(t10_47).add(t10_48)
        .add(t10_49).add(t10_50).add(t10_51).add(t10_52).add(t10_53).add(t10_54).add(t10_55).add(t10_56).add(t10_57).add(t10_58).add(t10_59).add(t10_60)
        .add(t10_61).add(t10_62).add(t10_63).add(t10_64).add(t10_65).add(t10_66).add(t10_67).add(t10_68).add(t10_69).add(t10_70).add(t10_71).add(t10_72)
        .add(t10_73).add(t10_74).add(t10_75).add(t10_76).add(t10_77);
    // ---
    return Tensors.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
  }
}
