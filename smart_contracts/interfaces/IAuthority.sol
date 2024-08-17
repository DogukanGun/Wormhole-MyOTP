// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

interface IAuthority {
    function generateChallenge() external returns(uint256);
    function verify(uint256 code) external returns(bool);
}