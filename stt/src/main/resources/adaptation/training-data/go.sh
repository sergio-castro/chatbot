#!/bin/bash

sphinx_fe -argfile en-us/feat.params \
        -samprate 16000 -c sample.fileids \
       -di . -do . -ei wav -eo mfc -mswav yes

pocketsphinx_mdef_convert -text en-us/mdef en-us/mdef.txt

./bw \
 -hmmdir en-us \
 -moddeffn en-us/mdef.txt \
 -ts2cbfn .ptm. \
 -feat 1s_c_d_dd \
 -svspec 0-12/13-25/26-38 \
 -cmn current \
 -agc none \
 -dictfn cmudict-en-us.dict \
 -ctlfn sample.fileids \
 -lsnfn sample.transcription \
 -accumdir .

./mllr_solve \
    -meanfn en-us/means \
    -varfn en-us/variances \
    -outmllrfn mllr_matrix -accumdir .

cp -a en-us en-us-adapt

./map_adapt \
    -moddeffn en-us/mdef.txt \
    -ts2cbfn .ptm. \
    -meanfn en-us/means \
    -varfn en-us/variances \
    -mixwfn en-us/mixture_weights \
    -tmatfn en-us/transition_matrices \
    -accumdir . \
    -mapmeanfn en-us-adapt/means \
    -mapvarfn en-us-adapt/variances \
    -mapmixwfn en-us-adapt/mixture_weights \
    -maptmatfn en-us-adapt/transition_matrices

./mk_s2sendump \
    -pocketsphinx yes \
    -moddeffn en-us-adapt/mdef.txt \
    -mixwfn en-us-adapt/mixture_weights \
    -sendumpfn en-us-adapt/sendump


