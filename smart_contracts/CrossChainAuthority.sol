// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;

import "./interfaces/IWormholeReceiver.sol";
import "./interfaces/IWormholeRelayer.sol";

abstract contract CrossChainAuthority is IWormholeReceiver {

    address authorityManagerAddress;

    event Verified(address userAddress);
    event NotVerified(address userAddress);

    constructor(address _authorityManagerAddress){
        _authorityManagerAddress = authorityManagerAddress;
    }

    function bytesToString(bytes memory payload)
        public
        pure
        returns (string memory)
    {
        return string(payload);
    }

    function compareStrings(string memory str1, string memory str2)
        public
        pure
        returns (bool)
    {
        return
            keccak256(abi.encodePacked(str1)) ==
            keccak256(abi.encodePacked(str2));
    }

    function bytesToAddress(bytes memory bys)
        public
        pure
        returns (address addr)
    {
        require(bys.length == 20, "Invalid length for an address");
        assembly {
            addr := mload(add(bys, 20))
        }
    }

    function bytes32ToAddress(bytes32 bys) public pure returns (address) {
        return address(uint160(uint256(bys)));
    }

    function receiveWormholeMessages(
        bytes memory payload,
        bytes[] memory additionalMessages,
        bytes32 sourceAddress,
        uint16 sourceChain,
        bytes32 deliveryHash
    ) external payable {
        string memory message = bytesToString(payload);
        //user address must be taken
        address sAddress = bytes32ToAddress(sourceAddress);
        require(authorityManagerAddress == sAddress,"Wrong source address.");
        if (compareStrings(message, "verified")) {
            emit Verified(sAddress);
        } else if (compareStrings(message, "not_verified")) {
            emit NotVerified(sAddress);
        }
    }
}
