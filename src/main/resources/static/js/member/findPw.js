$(document).ready(() => {

    const phoneNumberPattern = /^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$/; // 휴대폰 형식검사 정규 표현식
    const pwd_pattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; // 패스워드 정규 표현식
    var auth_num = ""; // 인증번호요청을 했는지 확인하는 체크값.
    var user_number = ""; // 인증번호 요청이 성공했을때 가지고 있어야할 유저 넘버.

    // 아이디 / 비밀번호 탭 이동 기능 구현
    var $tablink = $('.idpw-box-tabs div').click(function (e) {
        var idx = $tablink.index(this);
        $('.idpw-box-tabs div').css('color', 'rgb(170, 170, 170)');
        $('.idpw-box-tabs div').css('font-weight', '400');
        $(this).css('color', 'black');
        $(this).css('font-weight', '600');

        var marginLeftValue = idx * 50;
        $('.idpw-box-tabs-clicked-bar').animate({
            'margin-left': marginLeftValue + '%'
        }, 200)


    });

    $('#find-id-tab').click(() => {

        console.log("아이디 버튼클릭.")
        $('.idpw-container-inner1').removeClass('disappear');
        $('.idpw-container-inner2').addClass('disappear');
    })

    $('#find-pw-tab').click(() => {

        $('.idpw-container-inner2').removeClass('disappear');
        $('.idpw-container-inner1').addClass('disappear');

    })


    // 아이디 (이메일 주 소)받기 버튼 클릭시.
    $('#find-id-button').click(() => {

        var query = {
            user_name: $('[name=user_name]').val(),
            user_phone1: $('[name=user_phone1]').val()

        }

        console.log(query);

        $.ajax({

            url: '/member/requestId',
            method: 'POST',
            data: query,
            success: function (result) {
                console.log("requestid success");
                if (result !== "error") {

                    Swal.fire('찾으시는 아이디 : ' + result, '', 'info')


                } else if (result === "error") {

                    Swal.fire('조회된 아이디가 없습니다.', '', 'error')

                }

            }, error: function () {


            }

        })
    })

    // 비밀번호 재설정 인증번호 받기.
    $('#find-pw-button').click(() => {

        var query = {
            user_id: $('[name=user_id]').val(),
            user_phone2: $('[name=user_phone2]').val()
        };

        console.log(auth_num)

        if (phoneNumberPattern.test($('[name=user_phone2]').val())) { // 아직 인증번호를 받지 않았을 때만 인증번호를 전송한다.

            $.ajax({

                url: '/member/requestPw',
                method: 'POST',
                data: query,
                success: function (result) {

                    if (result !== 'error') { // 유저정보가 있다면 해당 유저 정보가 뭔지 가지고 와야한다.

                        var ran_num = result.split('/')[0]; // 생성된 난수.
                        user_number = result.split('/')[1]; // 회원번호.
                        // 지금은 콘솔창을 통해 확인하고자 난수를 전달받는다. 나중에는 유저number만 전달받는다.

                        // 아이디와 휴대폰번호에 일치하는 유저정보가 있다는 뜻이므로 실제로
                        // 인증번호를 전송해주고 모달창이나 아래쪽 div를 열어준다.
                        // 혹은 모달창을 띄워준다.

                        $('#auth_block').removeClass('disappear');
                        $('#find-pw-block1').addClass('disappear'); // 첫번째 버튼 없앰.
                        $('#find-pw-block2').removeClass('disappear'); // 첫번째 버튼 없앰.

                        console.log(ran_num);
                        console.log(user_number);
                        auth_num = ran_num;

                        $('[name=user_id]').prop('disabled', true);
                        $('[name=user_phone2]').prop('disabled', true);


                    } else {

                        Swal.fire("입력하신 아이디와 휴대폰 번호와 일치하는 회원정보가 없습니다.", '', 'error')
                    }

                }, error: function () {


                }

            })

        } else if (!phoneNumberPattern.test($('[name=user_phone2]').val())) { // 휴대폰번호가 틀려서 인증요청이 실패한 경우.

            Swal.fire("휴대폰 번호 형식이 유효하지 않습니다.", '', 'error')

        }
    }) // 비밀번호 찾기 버튼 클릭시의 로직 끝


    $('#auth-button').click(() => { // 휴대폰 번호 인증하기 눌렷을떄.

        // 인증번호를 받았을 경우에는 같은 버튼을 클릭했다하더라도 인증번호로 인증하기 기능이 구현돼야한다.

        console.log("auth넘값." + auth_num);
        if (auth_num === $('#auth_num').val()) { // 만약에 에러뜨면 컨트롤러 반환타입이랑 input val타입이 다른 것.

            $('#update-block').removeClass('disappear'); // 나타난다
            $('#find-pw-block3').removeClass('disappear'); // 나타난다.
            $('.complete-auth-block').addClass('disappear'); // 없어진다.
            $('#auth_num').prop('disabled', true); // 인증이 완료되면 인증번호 수정을 막아버린다.


        } else { // 인증번호가 다를 떄
            Swal.fire('인증번호가 일치하지 않습니다.', '', 'error')

        }


    })

    $('#update-pw-button').click(() => { // 비밀번호 변경하기 눌렀을 때


        var new_pw1 = $('#first-pw').val();
        var new_pw2 = $('#second-pw').val();

        var data = {

            user_number: user_number,
            new_pw1: new_pw1
        }
        if (!pwd_pattern.test(new_pw1)) {

            Swal.fire('비밀번호 형식에 맞게 작성해주세요', '', 'error');

        } else if (new_pw1 !== new_pw2) {

            Swal.fire('1차 비밀번호와 2차 비밀번호는 일치해야 합니다.', '', 'error');

        } else if (new_pw1 === new_pw2 && pwd_pattern.test(new_pw1)) { // 비밀번호 유효성 검사.

            $.ajax({

                url: '/member/updatePw',
                data: data,
                method: 'POST',
                success: function (result) {

                    console.log("result " + result);
                    if (result === "1") {

                        Swal.fire('비밀번호가 변경됐습니다', '', 'success').then((result) => {

                            window.location.href = '/member/login';

                        })

                    } else {
                        Swal.fire('비밀번호 변경에 실패했습니다.', '', 'error')
                    }
                    // location 처리해야할 수도 있음.


                }, error: function () {
                    Swal.fire('에러가 발생했습니다.(관리자 문의)', '', 'error')

                }
            })
        } else { // 입력한 패스워드가 다를 경우.

            Swal.fire('1차 비밀번호가 2차 비밀번호가 일치하지 않습니다.', '', 'error')

        }

    })


})