#!/system/bin/sh
# Copyright (c) 2011-2013, The Linux Foundation. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#     * Redistributions of source code must retain the above copyright
#       notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above
#       copyright notice, this list of conditions and the following
#       disclaimer in the documentation and/or other materials provided
#       with the distribution.
#     * Neither the name of The Linux Foundation nor the names of its
#       contributors may be used to endorse or promote products derived
#       from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
# ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
# BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
# BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
# OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
# IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
#

dir0=/data/usf
pcm_ind_file=$dir0/pcm_inds.txt

if [ ! -e $pcm_ind_file ]; then
   # Form factor oriented PCM ports definition
   pcm_list=`cat /proc/asound/pcm`
   tx_rx_patterns="tx2- rx2-"
   result=""

   for pattern in $tx_rx_patterns; do
       echo $pattern
       ind="${pcm_list##*"$pattern"}"

       case "$pcm_list" in
           "$ind")
           ind="0"
           ;;

           *)
           ind="${ind/ *}"
           ;;
       esac
       result=$result$ind" "
   done
   echo $result>$pcm_ind_file
   # Change permission for pcm_inds so that it could be only read by everyone
   chmod 0444 $pcm_ind_file
fi

# Post-boot start of selected USF based calculators
for i in $(cat $dir0/auto_start.txt); do
   start $i
done
