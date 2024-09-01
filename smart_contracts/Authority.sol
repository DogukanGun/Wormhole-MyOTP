// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

import "./interfaces/IAuthority.sol";

contract Authority is IAuthority{

    address manager;
    uint256 activeVerification;
    bool waitingVerification;

    constructor(address _manager)
        
    {
        manager = _manager;
    }

    modifier only_manager() {
        require(msg.sender == manager,"Only manager can call this function");
        _;
    }

    function verify(uint256 code) external only_manager returns(bool){
        require(waitingVerification,"You don't have active verification");
        bool isVerified = activeVerification == code;
        waitingVerification = !isVerified;
        return isVerified;
    }

    function generateChallenge() external only_manager returns(uint256) {
        uint256 random = uint256(keccak256(abi.encodePacked(block.timestamp, msg.sender))) % 1000000;
        activeVerification = random;
        waitingVerification = true;
        return random;
    }
}